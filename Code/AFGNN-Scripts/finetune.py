import argparse

from loader import MoleculeDataset
from torch_geometric.loader import DataLoader

import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim

from tqdm import tqdm
import numpy as np

from model import GNN, GNN_graphpred
from sklearn.metrics import roc_auc_score

from splitters import scaffold_split, random_scaffold_split, random_split, custom_split
import pandas as pd

import os
import sys
import shutil

## NEW IMPORTS
import random
import platform
from sklearn.metrics import (
    recall_score,
    precision_score,
    f1_score,
    accuracy_score,
    matthews_corrcoef,
)

from plot_results import single_line_plot, two_lines_plot

from model_ng import CustomGeneraicGNN_encoder

from transformers import get_linear_schedule_with_warmup
    
def plot_training_and_validation_loss(training_loss, validation_loss, epochs, location):
    two_lines_plot(epochs, training_loss, epochs, validation_loss, "Training Loss", "Validation Loss", "Epoch", "Loss", "Training and Validation Loss", location + "/traing_valid_loss.jpg")

def plot_training_and_test_results(training_result, test_result, epochs, location):
    training_acc = [float("{:.2f}".format(res[0] * 100)) for res in training_result]
    training_f1 = [float("{:.2f}".format(res[3] * 100)) for res in training_result]
    testing_acc = [float("{:.2f}".format(res[0] * 100)) for res in test_result]
    testing_f1 = [float("{:.2f}".format(res[3] * 100)) for res in test_result]
    two_lines_plot(epochs, training_acc, epochs, testing_acc, "Training Accuracy", "Validation Accuracy", "Epoch", "Accuracy", "Training and Validation Accuracy", location + "/traing_valid_accuracy.jpg")
    two_lines_plot(epochs, training_f1, epochs, testing_f1, "Training F1", "Validation F1", "Epoch", "F1 Score", "Training and Validation F1 Scores", location + "/traing_valid_f1.jpg")

def accuracy(y, pred):
    return accuracy_score(y, pred)

def f1(y, pred, average=None):
    if average:
        return f1_score(y, pred, average=average, zero_division=0)
    return f1_score(y, pred, average="weighted", zero_division=0)

def recall(y, pred, average=None):
    if average:
        return recall_score(y, pred, average=average, zero_division=0)
    return recall_score(y, pred, average="weighted", zero_division=0)

def precision(y, pred, average=None):
    if average:
        return precision_score(y, pred, average=average, zero_division=0)
    return precision_score(y, pred, average="weighted", zero_division=0)

def performance(y, pred, average=None):
    acc = accuracy(y, pred)
    f = f1(y, pred, average=average)
    re =recall(y, pred, average=average)
    pre = precision(y, pred, average=average)
    return acc, pre, re, f

def train_binary_classification(args, model, device, loader, optimizer):
    criterion = nn.BCEWithLogitsLoss(reduction = "none")
    model.train()
    total_loss, count = 0, 0
    for step, batch in enumerate(tqdm(loader, desc="Iteration")):
        batch = batch.to(device)
        pred = model(batch.x, batch.edge_index, batch.edge_attr, batch.batch)
        y = batch.y.view(pred.shape).to(torch.float64)

        # #Whether y is non-null or not.
        # is_valid = y**2 > 0
        # #Loss matrix
        # loss_mat = criterion(pred.double(), (y+1)/2)
        # #loss matrix after removing null target
        # loss_mat = torch.where(is_valid, loss_mat, torch.zeros(loss_mat.shape).to(loss_mat.device).to(loss_mat.dtype))
        loss_mat = criterion(pred.double(), y)
            
        optimizer.zero_grad()
        # loss = torch.sum(loss_mat)/torch.sum(is_valid)
        loss = torch.sum(loss_mat)/len(loss_mat)
        total_loss += loss
        loss.backward()

        optimizer.step()
        count += 1

    print("Training Loss:", total_loss/count)
    return (total_loss/count).detach().cpu().numpy()
    
def train_multiclass_classification(args, model, device, loader, optimizer):
    criterion = nn.CrossEntropyLoss()
    model.train()
    y_true, y_scores = [], []
    total_loss, count = 0, 0
    for step, batch in enumerate(tqdm(loader, desc="Iteration")):
        batch = batch.to(device)
        if args.gnn_type in ["rgat", "rgcn", "gcnw"]:
            pred = model(x = batch.x, edge_index = batch.edge_index, batch = batch.batch, edge_type = batch.edge_attr, classify = True)
        else:
            pred = model(x = batch.x, edge_index = batch.edge_index, batch = batch.batch, classify = True)
        #y = batch.y.view(pred.shape).to(torch.float64)
        
        y_true.append(batch.y.cpu())
        _, predicted_label = torch.max(pred, dim=1)
        y_scores.append(predicted_label.cpu())

        # #Whether y is non-null or not.
        # is_valid = y**2 > 0
        # #Loss matrix
        # loss_mat = criterion(pred.double(), (y+1)/2)
        # #loss matrix after removing null target
        # loss_mat = torch.where(is_valid, loss_mat, torch.zeros(loss_mat.shape).to(loss_mat.device).to(loss_mat.dtype))
        loss = criterion(pred.double(), batch.y)
        
        # loss = torch.sum(loss_mat)/torch.sum(is_valid)
        # loss = torch.sum(loss_mat)/len(loss_mat)
        total_loss += loss
        loss.backward()
        optimizer.step()
        optimizer.zero_grad()
        count += 1
        
    y_true = torch.cat(y_true, dim = 0).cpu().numpy()
    y_scores = torch.cat(y_scores, dim = 0).cpu().numpy()
    #y_pred = [1 if i > 0 else 0 for i in y_scores]
    acc, prec, rec, f1 = performance(y_true, y_scores)
    avg_loss = total_loss/count 

    print("Training Loss:", total_loss/count)
    return (acc, prec, rec, f1), avg_loss.detach().cpu().numpy()

def eval_binary_classification(args, model, device, loader):
    criterion = nn.BCEWithLogitsLoss(reduction = "none")
    model.eval()
    y_true = []
    y_scores = []
    total_loss, count = 0, 0
    for step, batch in enumerate(tqdm(loader, desc="Iteration")):
        batch = batch.to(device)

        with torch.no_grad():
            pred = model(batch.x, batch.edge_index, batch.edge_attr, batch.batch)

        y_true.append(batch.y.view(pred.shape))
        y_scores.append(pred)
        
        y = batch.y.view(pred.shape).to(torch.float64)
        loss = criterion(pred.double(), y)
        loss = torch.sum(loss)/len(loss)
        total_loss += loss
        count += 1

    y_true = torch.cat(y_true, dim = 0).cpu().numpy()
    y_scores = torch.cat(y_scores, dim = 0).cpu().numpy()
    y_pred = [1 if i > 0 else 0 for i in y_scores]
    acc, prec, rec, f1 = performance(y_true, y_pred)
    avg_loss = total_loss/count 

    # roc_list = []
    # for i in range(y_true.shape[1]):
    #     #AUC is only defined when there is at least one positive data.
    #     if np.sum(y_true[:,i] == 1) > 0 and np.sum(y_true[:,i] == -1) > 0:
    #         is_valid = y_true[:,i]**2 > 0
    #         roc_list.append(roc_auc_score((y_true[is_valid,i] + 1)/2, y_scores[is_valid,i]))

    # if len(roc_list) < y_true.shape[1]:
    #     print("Some target is missing!")
    #     print("Missing ratio: %f" %(1 - float(len(roc_list))/y_true.shape[1]))

    return (acc, prec, rec, f1), avg_loss.detach().cpu().numpy()

def eval_multiclass_classification(args, model, device, loader):
    criterion = nn.CrossEntropyLoss()
    model.eval()
    y_true = []
    y_scores = []
    total_loss, count = 0, 0
    
    for step, batch in enumerate(tqdm(loader, desc="Iteration")):
        batch = batch.to(device)

        with torch.no_grad():
            if args.gnn_type in ["rgat", "rgcn", "gcnw"]:
                pred = model(x = batch.x, edge_index = batch.edge_index, batch = batch.batch, edge_type = batch.edge_attr, classify = True)
            else:
                pred = model(x = batch.x, edge_index = batch.edge_index, batch = batch.batch, classify = True)

        # y_true.append(batch.y.view(pred.shape))
        # y_scores.append(pred)
        
        y_true.append(batch.y.cpu())
        _, predicted_label = torch.max(pred, dim=1)
        y_scores.append(predicted_label.cpu())
        
        loss = criterion(pred.double(), batch.y)
        total_loss += loss
        count += 1

    y_true = torch.cat(y_true, dim = 0).cpu().numpy()
    y_scores = torch.cat(y_scores, dim = 0).cpu().numpy()
    #y_pred = [1 if i > 0 else 0 for i in y_scores]
    acc, prec, rec, f1 = performance(y_true, y_scores)
    avg_loss = total_loss/count 
    
    return (acc, prec, rec, f1), avg_loss.detach().cpu().numpy()

def main():
    # Training settings
    parser = argparse.ArgumentParser(description='PyTorch implementation of pre-training of graph neural networks')
    parser.add_argument('--device', type=int, default=0,
                        help='which gpu to use if any (default: 0)')
    parser.add_argument('--batch_size', type=int, default=8,
                        help='input batch size for training (default: 32)')
    parser.add_argument('--epochs', type=int, default=10,
                        help='number of epochs to train (default: 100)')
    parser.add_argument('--lr', type=float, default=0.001,
                        help='learning rate (default: 0.001)')
    parser.add_argument('--lr_scale', type=float, default=1,
                        help='relative learning rate for the feature extraction layer (default: 1)')
    parser.add_argument('--decay', type=float, default=0,
                        help='weight decay (default: 0)')
    parser.add_argument('--num_layer', type=int, default=5,
                        help='number of GNN message passing layers (default: 5).')
    parser.add_argument('--emb_dim', type=int, default=300,
                        help='embedding dimensions (default: 300)')
    parser.add_argument('--dropout_ratio', type=float, default=0.5,
                        help='dropout ratio (default: 0.5)')
    parser.add_argument('--graph_pooling', type=str, default="mean",
                        help='graph level pooling (sum, mean, max, set2set, attention)')
    parser.add_argument('--JK', type=str, default="last",
                        help='how the node features across layers are combined. last, sum, max or concat')
    parser.add_argument('--gnn_type', type=str, default="gin")
    parser.add_argument('--dataset', type=str, default = 'crypto-api', help='root directory of dataset. For now, only classification.')
    parser.add_argument('--input_model_file', type=str, default = '', help='filename to read the model (if there is any)')
    parser.add_argument('--filename', type=str, default = '', help='output filename')
    parser.add_argument('--seed', type=int, default=42, help = "Seed for splitting the dataset.")
    parser.add_argument('--runseed', type=int, default=seed, help = "Seed for minibatch selection, random initialization.")
    parser.add_argument('--split', type = str, default="scaffold", help = "random or scaffold or random_scaffold")
    parser.add_argument('--eval_train', type=int, default = 1, help='evaluating training or not')
    parser.add_argument('--num_workers', type=int, default = 4, help='number of workers for dataset loading')
    parser.add_argument('--embedding_used', type=str, default = 'codebert', help='type of embedding to start with')
    args = parser.parse_args()
    
    os.environ["TOKENIZERS_PARALLELISM"] = "false"
    os.environ["CUDA_VISIBLE_DEVICES"] = "0,1"
    args.device = 1
    device = torch.device("cuda:" + str(args.device)) if torch.cuda.is_available() else torch.device("cpu")

    #Bunch of classification tasks
    args.dataset = "codenet-all-class" # Possible values: "bug-detection", "crypto-api", "codenet-5-class", "codenet-10-class", "codenet-100-class", "codenet-all-class", "codenet-all-class-30", "codenet-all-class-50"
    if args.dataset == "crypto-api" or args.dataset == "bug-detection":
        args.num_tasks = 2
    elif args.dataset == "codenet-5-class":
        args.num_tasks = 5
    elif args.dataset == "codenet-10-class":
        args.num_tasks = 10
    elif args.dataset == "codenet-20-class":
        args.num_tasks = 20
    elif args.dataset == "codenet-100-class":
        args.num_tasks = 100
    elif args.dataset.startswith("codenet-all-class"):
        args.num_tasks = 249
    else:
        raise ValueError("Invalid dataset name.")

    #set up dataset and transform function.
    dataset_root = "./API-Minsuse/Repository/Graph-Models/AFGNN/dataset"
    args.output_plots = "./API-Minsuse/Repository/Graph-Models/AFGNN/output/plots"
    #args.output_model_file = "./API-Minsuse/Repository/Graph-Models/AFGNN/output/saved_models/fine-tuned"
    
    #set up dataset
    dataset = MoleculeDataset(dataset_root, dataset=args.dataset)
    print("Dataset Loaded!!!")
    print(dataset)
    
    args.split = "custom"
    if args.split == "custom":
        train_dataset, valid_dataset, test_dataset = custom_split(dataset)
        print("custom split")
    elif args.split == "scaffold":
        smiles_list = pd.read_csv('dataset/' + args.dataset + '/processed/smiles.csv', header=None)[0].tolist()
        train_dataset, valid_dataset, test_dataset = scaffold_split(dataset, smiles_list, null_value=0, frac_train=0.8,frac_valid=0.1, frac_test=0.1)
        print("scaffold")
    elif args.split == "random":
        train_dataset, valid_dataset, test_dataset = random_split(dataset, null_value=0, frac_train=0.8,frac_valid=0.1, frac_test=0.1, seed = args.seed)
        print("random")
    elif args.split == "random_scaffold":
        smiles_list = pd.read_csv('dataset/' + args.dataset + '/processed/smiles.csv', header=None)[0].tolist()
        train_dataset, valid_dataset, test_dataset = random_scaffold_split(dataset, smiles_list, null_value=0, frac_train=0.8,frac_valid=0.1, frac_test=0.1, seed = args.seed)
        print("random scaffold")
    else:
        raise ValueError("Invalid split option.")

    print(train_dataset[0])

    if args.dataset == "crypto-api":
        args.batch_size = 4
    elif args.dataset.startswith("codenet") or args.dataset == "bug-detection":
        args.batch_size = 32
    else:
        args.batch_size = 8
    train_loader = DataLoader(train_dataset, batch_size=args.batch_size, shuffle=True, num_workers = args.num_workers)
    val_loader = DataLoader(valid_dataset, batch_size=args.batch_size, shuffle=False, num_workers = args.num_workers)
    test_loader = DataLoader(test_dataset, batch_size=args.batch_size, shuffle=False, num_workers = args.num_workers)

    #set up model
    args.num_layer = 5
    args.embedding_used = "codet5p"
    if args.embedding_used == "codet5p":
        args.emb_dim = 256
    elif args.embedding_used in ["codebert", "unixcoder"]:
        args.emb_dim = 768
    args.JK = "last" 
    args.dropout_ratio = 0
    args.graph_pooling = "attention"
    args.gnn_type = "rgcn" # Supported model types are "gcn", "gat", "gatv2", "gin", "rgcn", "rgat", "gcnw"
    model = CustomGeneraicGNN_encoder(args.num_layer, args.emb_dim, args.JK, args.dropout_ratio, args.gnn_type, args.graph_pooling, args.num_tasks).to(device)
    print("Model: ", model)
    
    args.input_model_file = "./API-Minsuse/Repository/Graph-Models/AFGNN/output/saved_models/context-prediction/rgcn_1_5_7_e19_code2seq_1.3M_atleast_3_edges_CodeT5+_sub_model.pth"
    if not args.input_model_file == "":
        model.from_pretrained(args.input_model_file)
        print("Loaded the model!!")
    model.to(device)

    #set up optimizer
    #different learning rate for different part of GNN
    args.lr = 1 * 0.0001
    model_param_group = []
    model_param_group.append({"params": model.gnn.parameters()})
    if args.graph_pooling == "attention":
        model_param_group.append({"params": model.pool.parameters(), "lr":args.lr*args.lr_scale})
    model_param_group.append({"params": model.graph_pred_linear.parameters(), "lr":args.lr*args.lr_scale})
    optimizer = optim.Adam(model_param_group, lr=args.lr, weight_decay=args.decay)
    
    args.epochs = 200
    args.max_steps=args.epochs*len(train_loader)
    args.warmup_steps=args.max_steps//20
    # scheduler = get_linear_schedule_with_warmup(optimizer, num_warmup_steps=args.warmup_steps, num_training_steps=args.max_steps)
    
    optimizer.zero_grad()
    print(optimizer)

    train_acc_list = []
    val_acc_list = []
    training_loss_list = []
    epochs_list = []
    validation_loss_list = []

    best_validation_acc, patience = 0, 0
    best_validation_model = None
    print("\nArguments: ", args)
    for epoch in range(1, args.epochs):
        print("\n====epoch " + str(epoch))
        
        if args.num_tasks >= 2:
            train_acc, training_loss = train_multiclass_classification(args, model, device, train_loader, optimizer)
        else:
            train_acc, training_loss = train_binary_classification(args, model, device, train_loader, optimizer)
            
        if args.num_tasks >= 2:
            val_acc, val_loss = eval_multiclass_classification(args, model, device, val_loader)
        else:
            val_acc, val_loss = eval_binary_classification(args, model, device, val_loader)
            
        if best_validation_acc < val_acc[3]:
            best_validation_acc = val_acc[3]
            best_validation_model = model.state_dict()
            print("\n======F1 score improved, saving the model")
            patience = 0
        else:
            patience += 1
            if patience == 10:
                print("\nStopping training after {} epochs as the score didn't improve in consecutive {} epochs".format(epoch+1, patience))
                break

        print("Train(acc, prec, rec, f1): {} and Val(acc, prec, rec, f1): {}".format(train_acc, val_acc))
        print("Training Loss: {} and Validation Loss: {}".format(training_loss, val_loss))

        val_acc_list.append(val_acc)
        train_acc_list.append(train_acc)
        epochs_list.append(epoch+1)
        training_loss_list.append(training_loss)
        validation_loss_list.append(val_loss)
    
    print("\n========= Evaluation (Best Model) on Test Data =========")
    model.load_state_dict(best_validation_model)
    if args.num_tasks >= 2:
        test_acc, test_loss = eval_multiclass_classification(args, model, device, test_loader)
    else:
        test_acc, test_loss = eval_binary_classification(args, model, device, test_loader)
    print("Test(acc, prec, rec, f1): {}".format(test_acc))
    
    plot_training_and_validation_loss(training_loss_list, validation_loss_list, epochs_list, args.output_plots)
    plot_training_and_test_results(train_acc_list, val_acc_list, epochs_list, args.output_plots)
        
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
    main()
