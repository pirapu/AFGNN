import argparse

from loader import MoleculeDataset

import os
import sys
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim

from tqdm import tqdm
import numpy as np

#from model import GNN
from model import GNN
from sklearn.metrics import roc_auc_score

from splitters import custom_split, scaffold_split, random_split, random_scaffold_split
import pandas as pd

from util import ExtractSubstructureContextPair

from dataloader import DataLoaderSubstructContext

from torch_geometric.nn import global_add_pool, global_mean_pool, global_max_pool

## NEW IMPORTS
import gc
import random
import platform

from plot_results import single_line_plot, two_lines_plot
from model_ng import *

from torch.cuda.amp import autocast, GradScaler
scaler = GradScaler()

def plot_training_and_validation_loss(training_loss, validation_loss, epochs, location):
    two_lines_plot(epochs, training_loss, epochs, validation_loss, "Training Loss", "Validation Loss", "Epoch", "Loss", "Training and Validation Loss", location + "/traing_valid_loss.jpg")

def plot_training_and_test_results(training_result, test_result, epochs, location):
    two_lines_plot(epochs, training_result, epochs, test_result, "Training Accuracy", "Validation Accuracy", "Epoch", "Accuracy", "Training and Validation Accuracy", location + "/traing_validation_accuracy.jpg")

def pool_func(x, batch, mode = "sum"):
    if mode == "sum":
        return global_add_pool(x, batch)
    elif mode == "mean":
        return global_mean_pool(x, batch)
    elif mode == "max":
        return global_max_pool(x, batch)

def cycle_index(num, shift):
    arr = torch.arange(num) + shift
    arr[-shift:] = torch.arange(shift)
    return arr

def plot_training_loss_and_accuracy(training_loss, training_accuracy, epochs, location):
    training_accuracy = [float("{:.2f}".format(acc * 100)) for acc in training_accuracy]
    single_line_plot(epochs, training_loss, "Epoch", "Loss", "Training Loss vs Epoch", location + "/pre_training_loss.jpg")
    single_line_plot(epochs, training_accuracy, "Epoch", "Accuracy", "Training Accuracy vs Epoch", location + "/pre_training_accuracy.jpg")

criterion = nn.BCEWithLogitsLoss()

def train(args, model_substruct, model_context, loader, optimizer_substruct, optimizer_context, device_0, device_1):
    model_substruct.train()
    model_context.train()

    balanced_loss_accum = 0
    acc_accum = 0

    batches_skipped = 0
    for step, batch in enumerate(tqdm(loader, desc="Iteration")):
        
        if batch == None:
            print("\nbatch is none, skipping!!")
            batches_skipped += 1
            continue
        if args.filter_big_batches and (len(batch.x_substruct) > args.max_node_allowed_per_batch or len(batch.x_context) > args.max_node_allowed_per_batch):
            print("\nBatch node count is large, skipping!!")
            print("x_substruct: {} and x_context: {}".format(len(batch.x_substruct), len(batch.x_context)))
            print("edge_attr_substruct: {} and edge_attr_context: {}".format(len(batch.edge_attr_substruct), len(batch.edge_attr_context)))
            batches_skipped += 1
            continue
        if args.filter_big_batches and (len(batch.edge_attr_substruct) > args.max_edge_allowed_per_batch or len(batch.edge_attr_context) > args.max_edge_allowed_per_batch):
            print("\nBatch edge count is large, skipping!!")
            print("x_substruct: {} and x_context: {}".format(len(batch.x_substruct), len(batch.x_context)))
            print("edge_attr_substruct: {} and edge_attr_context: {}".format(len(batch.edge_attr_substruct), len(batch.edge_attr_context)))
            batches_skipped += 1
            continue
        #batch = batch.to(device_0)
        
        with autocast(dtype=torch.float16):
            # creating substructure and context representation
            # print(batch)
            # print("x_substruct: {} and x_context: {}".format(len(batch.x_substruct), len(batch.x_context)))
            # print("edge_attr_substruct: {} and edge_attr_context: {}".format(len(batch.edge_attr_substruct), len(batch.edge_attr_context)))
            # print("============================================================================================================================")
            if args.use_old:
                substruct_rep = model_substruct(batch.x_substruct, batch.edge_index_substruct, batch.edge_attr_substruct)[batch.center_substruct_idx]
                overlapped_node_rep = model_context(batch.x_context, batch.edge_index_context, batch.edge_attr_context)[batch.overlap_context_substruct_idx]
            else:
                if args.gnn_type in ["rgat", "rgcn", "gcnw"]:
                    batch = batch.to(device_0)
                    substruct_rep = model_substruct(batch.x_substruct.float(), batch.edge_index_substruct, torch.flatten(batch.edge_attr_substruct))[batch.center_substruct_idx]
                    batch = batch.to(device_1)
                    overlapped_node_rep = model_context(batch.x_context.float(), batch.edge_index_context, torch.flatten(batch.edge_attr_context))[batch.overlap_context_substruct_idx]
                    substruct_rep = substruct_rep.to(device_1)
                else:
                    batch = batch.to(device_0)
                    substruct_rep = model_substruct(batch.x_substruct.float(), batch.edge_index_substruct)[batch.center_substruct_idx]
                    batch = batch.to(device_1)
                    overlapped_node_rep = model_context(batch.x_context.float(), batch.edge_index_context)[batch.overlap_context_substruct_idx]
                    substruct_rep = substruct_rep.to(device_1)
        
            #Contexts are represented by 
            if args.mode == "cbow":
                # positive context representation
                context_rep = pool_func(overlapped_node_rep, batch.batch_overlapped_context, mode = args.context_pooling)
                # negative contexts are obtained by shifting the indicies of context embeddings
                neg_context_rep = torch.cat([context_rep[cycle_index(len(context_rep), i+1)] for i in range(args.neg_samples)], dim = 0)
            
                pred_pos = torch.sum(substruct_rep * context_rep, dim = 1)
                pred_neg = torch.sum(substruct_rep.repeat((args.neg_samples, 1))*neg_context_rep, dim = 1)

            elif args.mode == "skipgram":

                expanded_substruct_rep = torch.cat([substruct_rep[i].repeat((batch.overlapped_context_size[i],1)) for i in range(len(substruct_rep))], dim = 0)
                pred_pos = torch.sum(expanded_substruct_rep * overlapped_node_rep, dim = 1)

                #shift indices of substructures to create negative examples
                shifted_expanded_substruct_rep = []
                for i in range(args.neg_samples):
                    shifted_substruct_rep = substruct_rep[cycle_index(len(substruct_rep), i+1)]
                    shifted_expanded_substruct_rep.append(torch.cat([shifted_substruct_rep[i].repeat((batch.overlapped_context_size[i],1)) for i in range(len(shifted_substruct_rep))], dim = 0))

                shifted_expanded_substruct_rep = torch.cat(shifted_expanded_substruct_rep, dim = 0)
                pred_neg = torch.sum(shifted_expanded_substruct_rep * overlapped_node_rep.repeat((args.neg_samples, 1)), dim = 1)

            else:
                raise ValueError("Invalid mode!")

            loss_pos = criterion(pred_pos.double(), torch.ones(len(pred_pos)).to(pred_pos.device).double())
            loss_neg = criterion(pred_neg.double(), torch.zeros(len(pred_neg)).to(pred_neg.device).double())
            loss = loss_pos + args.neg_samples*loss_neg
            loss = loss / args.gradient_accumulation_steps
        
        scaler.scale(loss).backward()
        #To write: optimizer
        # optimizer_substruct.step()
        # optimizer_context.step()
        if (step + 1) % args.gradient_accumulation_steps == 0 or (step + 1) == len(loader):
            # optimizer_substruct.step()
            # optimizer_context.step()
            scaler.step(optimizer_substruct)
            scaler.step(optimizer_context)
            scaler.update()
            optimizer_substruct.zero_grad()
            optimizer_context.zero_grad()

        balanced_loss_accum += float(loss_pos.detach().cpu().item() + loss_neg.detach().cpu().item())
        acc_accum += 0.5* (float(torch.sum(pred_pos > 0).detach().cpu().item())/len(pred_pos) + float(torch.sum(pred_neg < 0).detach().cpu().item())/len(pred_neg))
        
        del batch
        del substruct_rep
        del overlapped_node_rep
        del context_rep
        del neg_context_rep
        del pred_pos
        del pred_neg
        del loss_pos
        del loss_neg
        del loss
        # gc.collect()
        # torch.cuda.empty_cache()

    print("Total batches skipped: ", batches_skipped)
    return balanced_loss_accum/(step+1), acc_accum/(step+1)


def eval(args, model_substruct, model_context, loader, optimizer_substruct, optimizer_context, device_0, device_1):
    model_substruct.eval()
    model_context.eval()

    balanced_loss_accum = 0
    acc_accum = 0
    batches_skipped = 0

    for step, batch in enumerate(tqdm(loader, desc="Iteration")):
        if batch == None:
            print("\nbatch is none, skipping!!")
            batches_skipped += 1
            continue
        if args.filter_big_batches and (len(batch.x_substruct) > args.max_node_allowed_per_batch or len(batch.x_context) > args.max_node_allowed_per_batch):
            print("\nBatch node count is large, skipping!!")
            print("x_substruct: {} and x_context: {}".format(len(batch.x_substruct), len(batch.x_context)))
            print("edge_attr_substruct: {} and edge_attr_context: {}".format(len(batch.edge_attr_substruct), len(batch.edge_attr_context)))
            batches_skipped += 1
            continue
        if args.filter_big_batches and (len(batch.edge_attr_substruct) > args.max_edge_allowed_per_batch or len(batch.edge_attr_context) > args.max_edge_allowed_per_batch):
            print("\nBatch edge count is large, skipping!!")
            print("x_substruct: {} and x_context: {}".format(len(batch.x_substruct), len(batch.x_context)))
            print("edge_attr_substruct: {} and edge_attr_context: {}".format(len(batch.edge_attr_substruct), len(batch.edge_attr_context)))
            batches_skipped += 1
            continue
        #batch = batch.to(device)

        # creating substructure and context representation
        if args.use_old:
            substruct_rep = model_substruct(batch.x_substruct, batch.edge_index_substruct, batch.edge_attr_substruct)[batch.center_substruct_idx]
            overlapped_node_rep = model_context(batch.x_context, batch.edge_index_context, batch.edge_attr_context)[batch.overlap_context_substruct_idx]
        else:
            if args.gnn_type in ["rgat", "rgcn", "gcnw"]:
                batch = batch.to(device_0)
                substruct_rep = model_substruct(batch.x_substruct.float(), batch.edge_index_substruct, torch.flatten(batch.edge_attr_substruct))[batch.center_substruct_idx]
                batch = batch.to(device_1)
                overlapped_node_rep = model_context(batch.x_context.float(), batch.edge_index_context, torch.flatten(batch.edge_attr_context))[batch.overlap_context_substruct_idx]
                substruct_rep = substruct_rep.to(device_1)
            else:
                batch = batch.to(device_0)
                substruct_rep = model_substruct(batch.x_substruct.float(), batch.edge_index_substruct)[batch.center_substruct_idx]
                batch = batch.to(device_1)
                overlapped_node_rep = model_context(batch.x_context.float(), batch.edge_index_context)[batch.overlap_context_substruct_idx]
                substruct_rep = substruct_rep.to(device_1)
                
        #Contexts are represented by 
        if args.mode == "cbow":
            # positive context representation
            context_rep = pool_func(overlapped_node_rep, batch.batch_overlapped_context, mode = args.context_pooling)
            # negative contexts are obtained by shifting the indicies of context embeddings
            neg_context_rep = torch.cat([context_rep[cycle_index(len(context_rep), i+1)] for i in range(args.neg_samples)], dim = 0)
            
            pred_pos = torch.sum(substruct_rep * context_rep, dim = 1)
            pred_neg = torch.sum(substruct_rep.repeat((args.neg_samples, 1))*neg_context_rep, dim = 1)

        elif args.mode == "skipgram":

            expanded_substruct_rep = torch.cat([substruct_rep[i].repeat((batch.overlapped_context_size[i],1)) for i in range(len(substruct_rep))], dim = 0)
            pred_pos = torch.sum(expanded_substruct_rep * overlapped_node_rep, dim = 1)

            #shift indices of substructures to create negative examples
            shifted_expanded_substruct_rep = []
            for i in range(args.neg_samples):
                shifted_substruct_rep = substruct_rep[cycle_index(len(substruct_rep), i+1)]
                shifted_expanded_substruct_rep.append(torch.cat([shifted_substruct_rep[i].repeat((batch.overlapped_context_size[i],1)) for i in range(len(shifted_substruct_rep))], dim = 0))

            shifted_expanded_substruct_rep = torch.cat(shifted_expanded_substruct_rep, dim = 0)
            pred_neg = torch.sum(shifted_expanded_substruct_rep * overlapped_node_rep.repeat((args.neg_samples, 1)), dim = 1)

        else:
            raise ValueError("Invalid mode!")

        loss_pos = criterion(pred_pos.double(), torch.ones(len(pred_pos)).to(pred_pos.device).double())
        loss_neg = criterion(pred_neg.double(), torch.zeros(len(pred_neg)).to(pred_neg.device).double())

        balanced_loss_accum += float(loss_pos.detach().cpu().item() + loss_neg.detach().cpu().item())
        acc_accum += 0.5* (float(torch.sum(pred_pos > 0).detach().cpu().item())/len(pred_pos) + float(torch.sum(pred_neg < 0).detach().cpu().item())/len(pred_neg))
        
        del batch
        del substruct_rep
        del overlapped_node_rep
        del context_rep
        del neg_context_rep
        del pred_pos
        del pred_neg
        del loss_pos
        del loss_neg
        # gc.collect()
        # torch.cuda.empty_cache()

    print("Total batches skipped: ", batches_skipped)
    return balanced_loss_accum/(step+1), acc_accum/(step+1)

def main():
    # Training settings
    parser = argparse.ArgumentParser(description='PyTorch implementation of pre-training of graph neural networks')
    parser.add_argument('--device', type=int, default=0,
                        help='which gpu to use if any (default: 0)')
    parser.add_argument('--batch_size', type=int, default=256,
                        help='input batch size for training (default: 256)')
    parser.add_argument('--epochs', type=int, default=100,
                        help='number of epochs to train (default: 100)')
    parser.add_argument('--lr', type=float, default=0.001,
                        help='learning rate (default: 0.001)')
    parser.add_argument('--decay', type=float, default=0,
                        help='weight decay (default: 0)')
    parser.add_argument('--num_layer', type=int, default=5,
                        help='number of GNN message passing layers (default: 5).')
    parser.add_argument('--csize', type=int, default=3,
                        help='context size (default: 3).')
    parser.add_argument('--emb_dim', type=int, default=768,
                        help='embedding dimensions (default: 300)')
    parser.add_argument('--dropout_ratio', type=float, default=0,
                        help='dropout ratio (default: 0)')
    parser.add_argument('--neg_samples', type=int, default=1,
                        help='number of negative contexts per positive context (default: 1)')
    parser.add_argument('--JK', type=str, default="last",
                        help='how the node features are combined across layers. last, sum, max or concat')
    parser.add_argument('--context_pooling', type=str, default="mean",
                        help='how the contexts are pooled (sum, mean, or max)')
    parser.add_argument('--mode', type=str, default = "cbow", help = "cbow or skipgram")
    parser.add_argument('--dataset', type=str, default = 'zinc_standard_agent', help='root directory of dataset for pretraining')
    parser.add_argument('--output_model_file', type=str, default = '', help='filename to output the model')
    parser.add_argument('--gnn_type', type=str, default="gcn")
    parser.add_argument('--seed', type=int, default=seed, help = "Seed for splitting dataset.")
    parser.add_argument('--num_workers', type=int, default = 8, help='number of workers for dataset loading')
    parser.add_argument('--gradient_accumulation_steps', type=int, default=1,
                        help="Number of updates steps to accumulate before performing a backward/update pass.")
    parser.add_argument('--embedding_used', type=str, default = 'codebert', help='type of embedding to start with')
    parser.add_argument('--input_model_file', type=str, default = '', help='filename to read the model (if there is any)')
    args = parser.parse_args()

    os.environ["CUDA_VISIBLE_DEVICES"] = "0,1"
    args.device = 1
    device = torch.device("cuda:" + str(args.device)) if torch.cuda.is_available() else torch.device("cpu")
    device_0 = torch.device("cuda:0")
    device_1 = torch.device("cuda:1")

    args.num_layer = 5
    args.csize = 6
    l1 = 1
    l2 = l1 + args.csize
    args.embedding_used = "codet5p"
    
    if args.embedding_used == "codet5p":
        args.emb_dim = 256
    elif args.embedding_used in ["codebert", "unixcoder"]:
        args.emb_dim = 768
        
    args.gnn_type = "gcn" # Supported model types are "gcn", "gat", "gatv2", "gin", "rgcn", "rgat", "gcnw"
    if args.gnn_type in ["rgat", "rgcn"]:
        args.batch_size = 8
        args.gradient_accumulation_steps = 8
        args.filter_big_batches = True
    else:
        args.batch_size = 256
        args.gradient_accumulation_steps = 1
        args.filter_big_batches = False
    args.max_node_allowed_per_batch = 500
    args.max_edge_allowed_per_batch = 20000
    print("mode: ", args.mode)
    print("GNN type: ", args.gnn_type)
    print("num layer: %d l1: %d l2: %d" %(args.num_layer, l1, l2))
    
    args.output_model_file = "./API-Minsuse/Repository/Graph-Models/AFGNN/output/saved_models/context-prediction"

    #set up dataset and transform function.
    args.dataset = "pdg_training_data"
    dataset_root = "./API-Minsuse/Repository/Graph-Models/AFGNN/dataset"
    args.output_plots = "./API-Minsuse/Repository/Graph-Models/AFGNN/output/plots"
    
    dataset = MoleculeDataset(dataset_root, dataset=args.dataset, transform = ExtractSubstructureContextPair(args.num_layer, l1, l2))
    os.environ["TOKENIZERS_PARALLELISM"] = "false"
    
    train_dataset, valid_dataset, test_dataset = custom_split(dataset, args.dataset)
    train_loader = DataLoaderSubstructContext(train_dataset, batch_size=args.batch_size, shuffle=True, num_workers = args.num_workers)
    valid_loader = DataLoaderSubstructContext(valid_dataset, batch_size=args.batch_size, shuffle=False, num_workers = args.num_workers)
    test_loader = DataLoaderSubstructContext(test_dataset, batch_size=args.batch_size, shuffle=False, num_workers = args.num_workers)

    #set up models, one for pre-training and one for context embeddings
    args.use_old = False
    if args.use_old:
        model_substruct = GNN(args.num_layer, args.emb_dim, JK = args.JK, drop_ratio = args.dropout_ratio, gnn_type = args.gnn_type).to(device)
        model_context = GNN(int(l2 - l1), args.emb_dim, JK = args.JK, drop_ratio = args.dropout_ratio, gnn_type = args.gnn_type).to(device)
    else:
        model_substruct = CustomGeneraicGNN(args.num_layer, args.emb_dim, args.JK, args.dropout_ratio, args.gnn_type).to(device_0)
        model_context = CustomGeneraicGNN(int(l2 - l1), args.emb_dim, args.JK, args.dropout_ratio, args.gnn_type).to(device_1)
    print("Subtruct Model: ", model_substruct)
    print("Context Model: ", model_context)
    
    #args.input_model_file = "./API-Minsuse/Repository/Graph-Models/AFGNN/output/saved_models/context-prediction"
    if not args.input_model_file == "":
        model_substruct.load_state_dict(torch.load(args.input_model_file + "/rgcn_1_5_7_e19_code2seq_1.3M_CodeT5+_sub_model.pth"))
        model_context.load_state_dict(torch.load(args.input_model_file + "/rgcn_1_5_7_e19_code2seq_1.3M_CodeT5+_con_model.pth"))
        print("Loaded the model!!")

    #set up optimizer for the two GNNs
    optimizer_substruct = optim.Adam(model_substruct.parameters(), lr=args.lr, weight_decay=args.decay)
    optimizer_context = optim.Adam(model_context.parameters(), lr=args.lr, weight_decay=args.decay)
    optimizer_substruct.zero_grad()
    optimizer_context.zero_grad()

    args.epochs = 100
    best_acc = 0
    training_acc_list, training_loss_list, epochs_list = [], [], []
    validation_acc_list, validation_loss_list = [], []
    print("\nArguments: ", args)
    for epoch in range(1, args.epochs+1):
        print("====epoch " + str(epoch))
        
        train_loss, train_acc = train(args, model_substruct, model_context, train_loader, optimizer_substruct, optimizer_context, device_0, device_1)
        with torch.no_grad():
            valid_loss, valid_acc = eval(args, model_substruct, model_context, valid_loader, optimizer_substruct, optimizer_context, device_0, device_1)
        print("Training Loss: {} and Validation Loss: {}".format(train_loss, valid_loss))
        print("Training Accuracy: {} and Validation Accuracy: {}".format(train_acc, valid_acc))
        
        validation_acc_list.append(valid_acc)
        training_acc_list.append(train_acc)
        epochs_list.append(epoch+1)
        training_loss_list.append(train_loss)
        validation_loss_list.append(valid_loss)
        
        if valid_acc > best_acc:
            best_acc = valid_acc
            print("\n======Accuracy score improved, saving the model")
            if not args.output_model_file == "":
                torch.save(model_substruct.state_dict(), args.output_model_file + "/sub_model.pth")
                torch.save(model_context.state_dict(), args.output_model_file + "/con_model.pth")
            patience = 0
        else:
            patience += 1
            if patience == 5:
                print("\nStopping training after {} epochs as the score didn't improve in consecutive {} epochs".format(epoch+1, patience))
                break
    
    print("========= Evaluation (Best Model) on Test Data =========")
    if not args.output_model_file == "":
        model_substruct.load_state_dict(torch.load(args.output_model_file + "/sub_model.pth"))
        model_context.load_state_dict(torch.load(args.output_model_file + "/con_model.pth"))
    with torch.no_grad():
        test_loss, test_acc = eval(args, model_substruct, model_context, test_loader, optimizer_substruct, optimizer_context, device_0, device_1)
    print("Final Test Accuracy : {} and Test Loss: {}".format(test_acc, test_loss))
    
    plot_training_and_validation_loss(training_loss_list, validation_loss_list, epochs_list, args.output_plots)
    plot_training_and_test_results(training_acc_list, validation_acc_list, epochs_list, args.output_plots)


# To ensure determinism
seed = 1234
def seed_everything(seed: int):
    random.seed(seed)
    np.random.seed(seed)
    torch.manual_seed(seed)
    torch.cuda.manual_seed_all(seed)
    os.environ['PYTHONHASHSEED'] = str(seed)
    torch.backends.cudnn.deterministic = True
seed_everything(seed)

# Check versions
print("Torch version: ", torch.__version__)
print("Torch cuda version: ", torch.version.cuda)
print("Python version: ", platform.python_version())

if __name__ == "__main__":
    #cycle_index(10,2)
    #just a comment
    main()
