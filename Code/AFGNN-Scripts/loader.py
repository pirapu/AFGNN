import os
import gc
import glob
import math
import torch
import pickle
import collections
import math
import pandas as pd
import numpy as np
import networkx as nx
from rdkit import Chem
from rdkit.Chem import Descriptors
from rdkit.Chem import AllChem
from rdkit import DataStructs
from rdkit.Chem.rdMolDescriptors import GetMorganFingerprintAsBitVect
from torch.utils import data
from torch_geometric.data import Data
from torch_geometric.data import InMemoryDataset
from torch_geometric.data import Batch
from itertools import repeat, product, chain


## NEW IMPORTS

import sys
import tqdm
import glob
from collections import OrderedDict
from transformers import AutoTokenizer, AutoModel
from unixcoder import UniXcoder


# allowable node and edge features
allowable_features = {
    'possible_atomic_num_list' : list(range(1, 119)),
    'possible_formal_charge_list' : [-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5],
    'possible_chirality_list' : [
        Chem.rdchem.ChiralType.CHI_UNSPECIFIED,
        Chem.rdchem.ChiralType.CHI_TETRAHEDRAL_CW,
        Chem.rdchem.ChiralType.CHI_TETRAHEDRAL_CCW,
        Chem.rdchem.ChiralType.CHI_OTHER
    ],
    'possible_hybridization_list' : [
        Chem.rdchem.HybridizationType.S,
        Chem.rdchem.HybridizationType.SP, Chem.rdchem.HybridizationType.SP2,
        Chem.rdchem.HybridizationType.SP3, Chem.rdchem.HybridizationType.SP3D,
        Chem.rdchem.HybridizationType.SP3D2, Chem.rdchem.HybridizationType.UNSPECIFIED
    ],
    'possible_numH_list' : [0, 1, 2, 3, 4, 5, 6, 7, 8],
    'possible_implicit_valence_list' : [0, 1, 2, 3, 4, 5, 6],
    'possible_degree_list' : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    'possible_bonds' : [
        Chem.rdchem.BondType.SINGLE,
        Chem.rdchem.BondType.DOUBLE,
        Chem.rdchem.BondType.TRIPLE,
        Chem.rdchem.BondType.AROMATIC
    ],
    'possible_bond_dirs' : [ # only for double bond stereo information
        Chem.rdchem.BondDir.NONE,
        Chem.rdchem.BondDir.ENDUPRIGHT,
        Chem.rdchem.BondDir.ENDDOWNRIGHT
    ]
}

def mol_to_graph_data_obj_simple(mol):
    """
    Converts rdkit mol object to graph Data object required by the pytorch
    geometric package. NB: Uses simplified atom and bond features, and represent
    as indices
    :param mol: rdkit mol object
    :return: graph data object with the attributes: x, edge_index, edge_attr
    """
    # atoms
    num_atom_features = 2   # atom type,  chirality tag
    atom_features_list = []
    for atom in mol.GetAtoms():
        atom_feature = [allowable_features['possible_atomic_num_list'].index(
            atom.GetAtomicNum())] + [allowable_features[
            'possible_chirality_list'].index(atom.GetChiralTag())]
        atom_features_list.append(atom_feature)
    x = torch.tensor(np.array(atom_features_list), dtype=torch.long)

    # bonds
    num_bond_features = 2   # bond type, bond direction
    if len(mol.GetBonds()) > 0: # mol has bonds
        edges_list = []
        edge_features_list = []
        for bond in mol.GetBonds():
            i = bond.GetBeginAtomIdx()
            j = bond.GetEndAtomIdx()
            edge_feature = [allowable_features['possible_bonds'].index(
                bond.GetBondType())] + [allowable_features[
                                            'possible_bond_dirs'].index(
                bond.GetBondDir())]
            edges_list.append((i, j))
            edge_features_list.append(edge_feature)
            edges_list.append((j, i))
            edge_features_list.append(edge_feature)

        # data.edge_index: Graph connectivity in COO format with shape [2, num_edges]
        edge_index = torch.tensor(np.array(edges_list).T, dtype=torch.long)

        # data.edge_attr: Edge feature matrix with shape [num_edges, num_edge_features]
        edge_attr = torch.tensor(np.array(edge_features_list),
                                 dtype=torch.long)
    else:   # mol has no bonds
        edge_index = torch.empty((2, 0), dtype=torch.long)
        edge_attr = torch.empty((0, num_bond_features), dtype=torch.long)

    data = Data(x=x, edge_index=edge_index, edge_attr=edge_attr)

    return data

def graph_data_obj_to_mol_simple(data_x, data_edge_index, data_edge_attr):
    """
    Convert pytorch geometric data obj to rdkit mol object. NB: Uses simplified
    atom and bond features, and represent as indices.
    :param: data_x:
    :param: data_edge_index:
    :param: data_edge_attr
    :return:
    """
    mol = Chem.RWMol()

    # atoms
    atom_features = data_x.cpu().numpy()
    num_atoms = atom_features.shape[0]
    for i in range(num_atoms):
        atomic_num_idx, chirality_tag_idx = atom_features[i]
        atomic_num = allowable_features['possible_atomic_num_list'][atomic_num_idx]
        chirality_tag = allowable_features['possible_chirality_list'][chirality_tag_idx]
        atom = Chem.Atom(atomic_num)
        atom.SetChiralTag(chirality_tag)
        mol.AddAtom(atom)

    # bonds
    edge_index = data_edge_index.cpu().numpy()
    edge_attr = data_edge_attr.cpu().numpy()
    num_bonds = edge_index.shape[1]
    for j in range(0, num_bonds, 2):
        begin_idx = int(edge_index[0, j])
        end_idx = int(edge_index[1, j])
        bond_type_idx, bond_dir_idx = edge_attr[j]
        bond_type = allowable_features['possible_bonds'][bond_type_idx]
        bond_dir = allowable_features['possible_bond_dirs'][bond_dir_idx]
        mol.AddBond(begin_idx, end_idx, bond_type)
        # set bond direction
        new_bond = mol.GetBondBetweenAtoms(begin_idx, end_idx)
        new_bond.SetBondDir(bond_dir)

    # Chem.SanitizeMol(mol) # fails for COC1=CC2=C(NC(=N2)[S@@](=O)CC2=NC=C(
    # C)C(OC)=C2C)C=C1, when aromatic bond is possible
    # when we do not have aromatic bonds
    # Chem.SanitizeMol(mol, sanitizeOps=Chem.SanitizeFlags.SANITIZE_KEKULIZE)

    return mol

def graph_data_obj_to_nx_simple(data):
    """
    Converts graph Data object required by the pytorch geometric package to
    network x data object. NB: Uses simplified atom and bond features,
    and represent as indices. NB: possible issues with recapitulating relative
    stereochemistry since the edges in the nx object are unordered.
    :param data: pytorch geometric Data object
    :return: network x object
    """
    G = nx.Graph()

    # atoms
    atom_features = data.x.cpu().numpy()
    num_atoms = atom_features.shape[0]
    for i in range(num_atoms):
        atomic_num_idx, chirality_tag_idx = atom_features[i]
        G.add_node(i, atom_num_idx=atomic_num_idx, chirality_tag_idx=chirality_tag_idx)
        pass

    # bonds
    edge_index = data.edge_index.cpu().numpy()
    edge_attr = data.edge_attr.cpu().numpy()
    num_bonds = edge_index.shape[1]
    for j in range(0, num_bonds, 2):
        begin_idx = int(edge_index[0, j])
        end_idx = int(edge_index[1, j])
        bond_type_idx, bond_dir_idx = edge_attr[j]
        if not G.has_edge(begin_idx, end_idx):
            G.add_edge(begin_idx, end_idx, bond_type_idx=bond_type_idx,
                       bond_dir_idx=bond_dir_idx)

    return G

def graph_data_obj_to_nx_simple_pdg(data):
    """
    Converts graph Data object required by the pytorch geometric package to
    network x data object. NB: Uses simplified atom and bond features,
    and represent as indices. NB: possible issues with recapitulating relative
    stereochemistry since the edges in the nx object are unordered.
    :param data: pytorch geometric Data object
    :return: network x object
    """
    G = nx.Graph()

    # node
    node_features = data.x.cpu().numpy()
    #node_inslength = data.ins_length.cpu().numpy()
    num_nodes = node_features.shape[0]
    for i in range(num_nodes):
        feat = node_features[i]
        G.add_node(i, feat = feat, ins_length = len(feat))
        

    # edge
    edge_index = data.edge_index.cpu().numpy()
    edge_attr = data.edge_attr.cpu().numpy()
    num_edges = edge_index.shape[1]
    for j in range(num_edges):
        begin_idx = int(edge_index[0, j])
        end_idx = int(edge_index[1, j])
        label = edge_attr[j]
        if not G.has_edge(begin_idx, end_idx):
            G.add_edge(begin_idx, end_idx, label=label)

    return G

def nx_to_graph_data_obj_simple(G):
    """
    Converts nx graph to pytorch geometric Data object. Assume node indices
    are numbered from 0 to num_nodes - 1. NB: Uses simplified atom and bond
    features, and represent as indices. NB: possible issues with
    recapitulating relative stereochemistry since the edges in the nx
    object are unordered.
    :param G: nx graph obj
    :return: pytorch geometric Data object
    """
    # atoms
    num_atom_features = 2  # atom type,  chirality tag
    atom_features_list = []
    for _, node in G.nodes(data=True):
        atom_feature = [node['atom_num_idx'], node['chirality_tag_idx']]
        atom_features_list.append(atom_feature)
    x = torch.tensor(np.array(atom_features_list), dtype=torch.long)

    # bonds
    num_bond_features = 2  # bond type, bond direction
    if len(G.edges()) > 0:  # mol has bonds
        edges_list = []
        edge_features_list = []
        for i, j, edge in G.edges(data=True):
            edge_feature = [edge['bond_type_idx'], edge['bond_dir_idx']]
            edges_list.append((i, j))
            edge_features_list.append(edge_feature)
            edges_list.append((j, i))
            edge_features_list.append(edge_feature)

        # data.edge_index: Graph connectivity in COO format with shape [2, num_edges]
        edge_index = torch.tensor(np.array(edges_list).T, dtype=torch.long)

        # data.edge_attr: Edge feature matrix with shape [num_edges, num_edge_features]
        edge_attr = torch.tensor(np.array(edge_features_list),
                                 dtype=torch.long)
    else:   # mol has no bonds
        edge_index = torch.empty((2, 0), dtype=torch.long)
        edge_attr = torch.empty((0, num_bond_features), dtype=torch.long)

    data = Data(x=x, edge_index=edge_index, edge_attr=edge_attr)

    return data

def nx_to_graph_data_obj_simple_pdg(G):
    """
    Converts nx graph to pytorch geometric Data object. Assume node indices
    are numbered from 0 to num_nodes - 1. NB: Uses simplified atom and bond
    features, and represent as indices. NB: possible issues with
    recapitulating relative stereochemistry since the edges in the nx
    object are unordered.
    :param G: nx graph obj
    :return: pytorch geometric Data object
    """
    # nodes, node feat, node type
    node_features_list = []
    ins_length_list = []
    for _, node in G.nodes(data=True):
        node_feature = node['feat']
        node_features_list.append(node_feature)
        ins_length_list.append( node["ins_length"] )
      
    #x = torch.tensor(np.array(node_features_list), dtype=torch.long )
    x = torch.from_numpy(np.array(node_features_list))
    ins_length = torch.tensor( np.array( ins_length_list), dtype=torch.long )

    # edges, edge type
    if len(G.edges()) > 0:  
        edges_list = []
        edge_label_list = []
        for i, j, edge in G.edges(data=True):
            edge_type =  edge['label'] 
            #edge_dir = edge['edge_dir']
            edges_list.append((i, j))
 
            edge_label_list.append([edge_type])
                                  
        edge_index = torch.tensor(np.array(edges_list).T, dtype=torch.long )
        edge_attr = torch.tensor(np.array(edge_label_list), dtype=torch.long)
    else:  
        edge_index = torch.empty((2, 0), dtype=torch.long)
        edge_attr = torch.empty((0, 2), dtype=torch.long)

    assert edge_index.shape[-1] == len(edge_attr), f"{edge_index}, {edge_attr.shape}, {len(G.nodes()) }"
    data = Data(x=x, edge_index=edge_index, edge_attr=edge_attr)
    data.ins_length = ins_length
    return data

def get_gasteiger_partial_charges(mol, n_iter=12):
    """
    Calculates list of gasteiger partial charges for each atom in mol object.
    :param mol: rdkit mol object
    :param n_iter: number of iterations. Default 12
    :return: list of computed partial charges for each atom.
    """
    Chem.rdPartialCharges.ComputeGasteigerCharges(mol, nIter=n_iter,
                                                  throwOnParamFailure=True)
    partial_charges = [float(a.GetProp('_GasteigerCharge')) for a in
                       mol.GetAtoms()]
    return partial_charges

def create_standardized_mol_id(smiles):
    """

    :param smiles:
    :return: inchi
    """
    if check_smiles_validity(smiles):
        # remove stereochemistry
        smiles = AllChem.MolToSmiles(AllChem.MolFromSmiles(smiles),
                                     isomericSmiles=False)
        mol = AllChem.MolFromSmiles(smiles)
        if mol != None: # to catch weird issue with O=C1O[al]2oc(=O)c3ccc(cn3)c3ccccc3c3cccc(c3)c3ccccc3c3cc(C(F)(F)F)c(cc3o2)-c2ccccc2-c2cccc(c2)-c2ccccc2-c2cccnc21
            if '.' in smiles: # if multiple species, pick largest molecule
                mol_species_list = split_rdkit_mol_obj(mol)
                largest_mol = get_largest_mol(mol_species_list)
                inchi = AllChem.MolToInchi(largest_mol)
            else:
                inchi = AllChem.MolToInchi(mol)
            return inchi
        else:
            return
    else:
        return

def get_nodes_edges(inTextFile, add_reverse_edges = False, api_name = None):
  # FD = 0, CD = 1
  # to support the hetero data object as suggested by the documentation 
  nodes_dict = OrderedDict()
  edge_indices_CD = []
  edge_indices_FD = []

  #to support the Data object as used by the Entities dat object as used in RGAT source code
  edge_indices = []
  edge_type = []
  
  # nodes_dict is an index_map
  node_count=0
  fp = open(inTextFile, "r")
  file_name = inTextFile.split("/")[-1].strip()
  Lines = fp.readlines()
  fp.close()
  
  # Capture the API nodes first
  number_of_api_nodes = 0
  if api_name != None:
    api_name = api_name[api_name.find("."):] + "("
    for line in Lines:
      nodes = line.split('-->')
      nodes[0], nodes[1] = nodes[0].strip(), nodes[1].strip()
      
      src = nodes[0]  
      if src not in nodes_dict.keys() and api_name in src:
        nodes_dict[src] = node_count
        node_count += 1
        number_of_api_nodes += 1
        
      right_idx = nodes[1].rfind('[')
      dst = nodes[1][:right_idx].strip()
      if dst not in nodes_dict.keys() and api_name in dst:
        nodes_dict[dst] = node_count
        node_count += 1
        number_of_api_nodes += 1
    if number_of_api_nodes == 0:
      print("No API Nodes found!!!!")
    
  # Process each edge
  for line in Lines:

      N = line.split('-->')
      N[0], N[1] = N[0].strip(), N[1].strip()
      
      #t1 = N[0].split('$$')   
      src = N[0].strip()   
      if src not in nodes_dict.keys():
        nodes_dict[src] = node_count
        node_count+=1
        
      #t2 = N[1].split('$$')
      right_idx = N[1].rfind('[')
      dst = N[1][:right_idx].strip()
      if dst not in nodes_dict.keys():
        nodes_dict[dst] = node_count
        node_count+=1

      x = N[1].strip()[right_idx + 1 : -1].strip()
      if(x == 'FD'):
        y=0
        edge_type.append(y)
        edge_indices.append([nodes_dict[src], nodes_dict[dst]])
        if add_reverse_edges:
          edge_type.append(y)
          edge_indices.append([nodes_dict[dst], nodes_dict[src]])
        edge_indices_FD.append([nodes_dict[src], nodes_dict[dst]])
      elif(x == 'CD'): 
        y=1
        edge_type.append(y)
        edge_indices.append([nodes_dict[src], nodes_dict[dst]])
        if add_reverse_edges:
          edge_type.append(y)
          edge_indices.append([nodes_dict[dst], nodes_dict[src]])
        edge_indices_CD.append([nodes_dict[src], nodes_dict[dst]])
      elif(x == 'SE'):
        y=2
        edge_type.append(y)
        edge_indices.append([nodes_dict[src], nodes_dict[dst]])
        if add_reverse_edges:
          edge_type.append(y)
          edge_indices.append([nodes_dict[dst], nodes_dict[src]])
      else:
          print("Edge type not found!!!")
     
  return nodes_dict, edge_indices_FD, edge_indices_CD, edge_indices, edge_type, file_name, number_of_api_nodes

#Set GPU
os.environ["CUDA_VISIBLE_DEVICES"] = "0,1"
device = torch.device('cuda:1' if torch.cuda.is_available() else 'cpu')
#device = torch.device("cpu")

# Initialize the models
codebert_tokenizer = AutoTokenizer.from_pretrained("microsoft/codebert-base")
codebert_model = AutoModel.from_pretrained("microsoft/codebert-base")
codebert_model = codebert_model.to(device)
codebert_model.eval()

def get_node_embedding_from_codebert(nodes):
    list_of_embeddings = []
    for code_line in nodes.keys():
        code_line = code_line.split("$$")[1].strip()
        code_tokens = codebert_tokenizer.tokenize(code_line, truncation=True, max_length=510)
        tokens = [codebert_tokenizer.cls_token]+code_tokens+[codebert_tokenizer.eos_token]
        tokens_ids = torch.tensor(codebert_tokenizer.convert_tokens_to_ids(tokens))
        tokens_ids = tokens_ids.to(device)
        context_embeddings = codebert_model(tokens_ids[None,:])
        cls_token_embedding = context_embeddings.last_hidden_state[0,0,:]
        list_of_embeddings.append(cls_token_embedding.to("cpu"))
        del tokens_ids
        del context_embeddings
        del cls_token_embedding
    # gc.collect()
    # torch.cuda.empty_cache()
    return torch.stack(list_of_embeddings)

def get_node_embedding_from_codebert_with_batching(nodes, batch_size = 8):
    list_of_embeddings = []
    code_lines = []
    for code_line in nodes.keys():
        code_line = code_line.split("$$")[1].strip()
        code_lines.append(code_line)
    batches = []
    batch_length  = int(math.ceil(len(code_lines) / batch_size))
    starting_index = 0
    for i in range(1, batch_length + 1):
        current_batch = code_lines[starting_index: min(len(code_lines), i*batch_size)]
        starting_index = min(len(code_lines), i*batch_size)
        batches.append(current_batch)
        
    for batch in batches:
        batch_tokens = codebert_tokenizer.batch_encode_plus(batch, 
                                                       return_tensors='pt',
                                                       padding='max_length', 
                                                       truncation=True, 
                                                       max_length=512)
        batch_token_ids, attention_masks = batch_tokens["input_ids"], batch_tokens["attention_mask"]
        batch_token_ids = batch_token_ids.to(device)
        attention_masks = attention_masks.to(device)
        context_embeddings = codebert_model(batch_token_ids, attention_masks)
        cls_token_embedding = context_embeddings.last_hidden_state[:,0,:]
        list_of_tensors = [cls_token_embedding[i, :] for i in range(cls_token_embedding.size()[0])]
        list_of_embeddings.extend(list_of_tensors)
        del attention_masks
        del batch_token_ids
        del context_embeddings
        del cls_token_embedding
        gc.collect()
        torch.cuda.empty_cache()
    return torch.stack(list_of_embeddings)

# Initialize the models
checkpoint = "Salesforce/codet5p-110m-embedding"
codet5p_tokenizer = AutoTokenizer.from_pretrained(checkpoint, trust_remote_code=True)
codet5p_model = AutoModel.from_pretrained(checkpoint, trust_remote_code=True).to(device)
codet5p_model.eval()

def get_node_embedding_from_codet5p(nodes):
    list_of_embeddings = []
    for code_line in nodes.keys():
        code_line = code_line.split("$$")[1].strip()
        inputs_tokens = codet5p_tokenizer.encode(code_line, return_tensors="pt").to(device)
        embedding = codet5p_model(inputs_tokens)[0]
        list_of_embeddings.append(embedding.to("cpu"))
        del inputs_tokens
        del embedding
    # gc.collect()
    # torch.cuda.empty_cache()
    return torch.stack(list_of_embeddings)

# Initialize the models
unixcoder_model = UniXcoder("microsoft/unixcoder-base")
unixcoder_model = unixcoder_model.to(device)
max_source_length = 512

def get_node_embedding_from_unixcoder(nodes):
    list_of_embeddings = []
    for code_line in nodes.keys():
        code_line = code_line.split("$$")[1].strip()
        tokens_ids = unixcoder_model.tokenize([code_line], max_length=512, mode="<encoder-only>")
        source_ids = torch.tensor(tokens_ids).to(device)
        tokens_embeddings, code_embedding = unixcoder_model(source_ids)
        code_embedding = code_embedding.squeeze(0)
        list_of_embeddings.append(code_embedding.to("cpu"))
    return torch.stack(list_of_embeddings)

def downsample(datapoints, pdg_folder_path):
    positive_points, negative_points = [], []
    for data in datapoints:
        id1, id2, label = data.strip().split("\t")
        pdg_code_1_path = pdg_folder_path + "/" + id1 + "_NA_NA_graph_dump.txt"
        pdg_code_2_path = pdg_folder_path + "/" + id2 + "_NA_NA_graph_dump.txt"
        if int(label) == 1:
            if os.path.exists(pdg_code_1_path) and os.path.exists(pdg_code_2_path): 
                positive_points.append([id1, id2, label])
        else:
            if os.path.exists(pdg_code_1_path) and os.path.exists(pdg_code_2_path):
                negative_points.append([id1, id2, label])
    print("Positive points: {} and Negative points: {}".format(len(positive_points), len(negative_points)))
    min_of_pos_neg = min(len(positive_points), len(negative_points))
    balanced_dataset = positive_points[:min_of_pos_neg] + negative_points[:min_of_pos_neg]
    return balanced_dataset

class MoleculeDataset(InMemoryDataset):
    def __init__(self,
                 root,
                 #data = None,
                 #slices = None,
                 transform=None,
                 pre_transform=None,
                 pre_filter=None,
                 dataset='zinc250k',
                 empty=False):
        """
        Adapted from qm9.py. Disabled the download functionality
        :param root: directory of the dataset, containing a raw and processed
        dir. The raw dir should contain the file containing the smiles, and the
        processed dir can either empty or a previously processed file
        :param dataset: name of the dataset. Currently only implemented for
        zinc250k, chembl_with_labels, tox21, hiv, bace, bbbp, clintox, esol,
        freesolv, lipophilicity, muv, pcba, sider, toxcast
        :param empty: if True, then will not load any data obj. For
        initializing empty dataset
        """
        self.dataset = dataset
        self.root = root

        super(MoleculeDataset, self).__init__(root, transform, pre_transform,
                                                 pre_filter)
        self.transform, self.pre_transform, self.pre_filter = transform, pre_transform, pre_filter

        if not empty:
            self.data, self.slices = torch.load(self.processed_paths[0])

    ################################## Not needed in A100 Server, uncomment for DGX1 if needed
    # def get(self, idx):
    #     data = Data()
    #     for key in self.data.keys:
    #         item, slices = self.data[key], self.slices[key]
    #         s = list(repeat(slice(None), item.dim()))
    #         s[data.__cat_dim__(key, item)] = slice(slices[idx],
    #                                                 slices[idx + 1])
    #         data[key] = item[s]
    #     return data


    @property
    def raw_file_names(self):
        file_name_list = os.listdir(self.raw_dir)
        # assert len(file_name_list) == 1     # currently assume we have a
        # # single raw file
        return file_name_list

    @property
    def processed_file_names(self):
        if self.dataset == "pdg_training_data":
            return 'gnn_data_code2seq_large_1.5M_atleast_3_edge_CodeT5+.pt'
        elif self.dataset == "clone-detection":
            return 'gnn_data_clone_detection_codet5+_after_fix.pt'
        elif self.dataset.startswith("codenet"):
            return 'gnn_data_{}_codet5+.pt'.format(self.dataset)
        elif self.dataset == "crypto-api":
            return 'gnn_data_crypto_api_codet5+.pt'
        elif self.dataset == "bug-detection":
            return 'gnn_data_bug_detection_codet5+.pt'

    def download(self):
        raise NotImplementedError('Must indicate valid location of raw data. '
                                  'No download allowed')

    def process(self):
        data_list = []
        if self.dataset == 'pdg_training_data':
            MINIMUM_NO_OF_EDGES = 3
            input_folder_paths = [path for path in self.raw_paths if "code2seq-large-1.5M" in path]
            all_api_folders = []
            file_count = 0
            for dataset_folder in input_folder_paths:
                all_api_folders.extend(glob.glob(dataset_folder + "/*/"))
                file_count += len(glob.glob(dataset_folder + "/*/*.txt"))
            # print("\nTotal pdg file count: ", file_count)
            count = 0
            data_cache = "./Temp/code2seq_1.5M_unixcoder_atleast_3_edges"
            for label, folder in tqdm.tqdm(enumerate(all_api_folders), total = len(all_api_folders)):
                print("\nAlready Processed Files: {}".format(count))
                print("\nProcessing: {}".format(folder))
                files = glob.glob(os.path.join(folder, '*.txt'))
                print("Number of files: {}\n".format(len(files)))
                for file in tqdm.tqdm(files):
                    
                    # Use graph data from cache
                    file_name = file.split("/")[-1].strip()
                    if os.path.exists(f'{data_cache}/{file_name}.pt'):
                        data = torch.load(f'{data_cache}/{file_name}.pt')
                        data_list.append(data)
                        count += 1
                        continue
                    
                    # Only process the files having atleast "MINIMUM_NO_OF_EDGES"
                    file_fp = open(file, "r")
                    lines_count = len(file_fp.readlines())
                    file_fp.close()
                    if lines_count < MINIMUM_NO_OF_EDGES:
                        continue
                        
                    try:
                        nodes_dict, edge_indices_FD, edge_indices_CD, edge_indices, edge_type, file_name, number_of_api_nodes = get_nodes_edges(file, add_reverse_edges = True, api_name = None)
                    except Exception as e:
                        print("\nError: ", e)
                        continue
                    
                    if(len(nodes_dict) == 0):
                        print("\nNo Data: ", file)
                        continue
                    #print(nodes_dict, edge_indices_CD, edge_indices_FD, edge_type)

                    # Node feature matrix with shape [num_nodes, num_node_features]=(N, 768).
                    try:
                        with torch.no_grad():
                            CodeEmbedding = get_node_embedding_from_unixcoder(nodes_dict)
                    except Exception as e :
                        print("\nError: ", e)
                        print(nodes_dict)
                        continue
                    # print(CodeEmbedding.shape)
                    # print(CodeEmbedding)

                    # FIXING DATA FOTMATS AND SHAPE
                    x = CodeEmbedding.clone().detach()
                    # print(x.shape)
  
                    # data.y: Target to train against (may have arbitrary shape),
                    # graph-level targets of shape [1, *]
                    y = torch.tensor([label], dtype=torch.long)
                    #print(type(y))

                    # edge_index (LongTensor, optional) – Graph connectivity in COO format with shape [2, num_edges]
                    edge_index_CD = torch.tensor(edge_indices_CD, dtype=torch.long).t().contiguous()
                    edge_index_FD = torch.tensor(edge_indices_FD, dtype=torch.long).t().contiguous()
                    edge_index = torch.tensor(edge_indices, dtype=torch.long).t().contiguous()
                    edge_attr = torch.tensor(edge_type, dtype=torch.long).t().contiguous()
                    data = Data(edge_index=edge_index, edge_attr=edge_attr, x=x)
                    data.id = torch.tensor([count])
                    data.y = y
                    data_list.append(data)
                    
                    torch.save(data, f'{data_cache}/{file_name}.pt')
                    count += 1
            
        elif self.dataset in ["crypto-api", "bug-detection"] or self.dataset.startswith("codenet"):
            splits = [["train", 1], ["valid", 2], ["test", 3]]
            input_folder_path = ""
            for folder_path in self.raw_paths:
                if self.dataset in folder_path:
                    break
            count = 0
            for split in splits:
                input_folder_path = folder_path + "/" + split[0]
                print("\nProcessing: {}\n".format(input_folder_path))
                files = glob.glob(os.path.join(input_folder_path, '*.txt'))
                print("\nNumber of files: {}\n".format(len(files)))
                for file in tqdm.tqdm(files):
                        
                    try:
                        nodes_dict, edge_indices_FD, edge_indices_CD, edge_indices, edge_type, file_name, number_of_api_nodes = get_nodes_edges(file, add_reverse_edges = True, api_name = None)
                    except Exception as e:
                        print("\nError: ", e)
                        continue
                    
                    if(len(nodes_dict) == 0):
                        print("\nNo Data: ", file)
                        continue
                    #print(nodes_dict, edge_indices_CD, edge_indices_FD, edge_type)

                    # Node feature matrix with shape [num_nodes, num_node_features]=(N, 768).
                    try:
                        with torch.no_grad():
                            CodeEmbedding = get_node_embedding_from_codet5p(nodes_dict)
                    except Exception as e :
                        print("\nError: ", e)
                        print(nodes_dict)
                        continue
                    # print(CodeEmbedding.shape)
                    # print(CodeEmbedding)

                    # FIXING DATA FOTMATS AND SHAPE
                    x = CodeEmbedding.clone().detach()
                    # print(x.shape)
  
                    # data.y: Target to train against (may have arbitrary shape),
                    # graph-level targets of shape [1, *]
                    file_name = file[file.rindex("/", 0, len(file) - 1) + 1 : -4]
                    if self.dataset in ["crypto-api", "bug-detection"]:
                        label = int(file_name.strip().split("_")[1])
                    else:
                        label = int(file_name.strip().split("_")[-1])
                    y = torch.tensor([label], dtype=torch.long)
                    #print(type(y))

                    # edge_index (LongTensor, optional) – Graph connectivity in COO format with shape [2, num_edges]
                    edge_index_CD = torch.tensor(edge_indices_CD, dtype=torch.long).t().contiguous()
                    edge_index_FD = torch.tensor(edge_indices_FD, dtype=torch.long).t().contiguous()
                    edge_index = torch.tensor(edge_indices, dtype=torch.long).t().contiguous()
                    edge_attr = torch.tensor(edge_type, dtype=torch.long).t().contiguous()
                    #print(edge_index_CD, edge_index_FD, edge_index, edge_type)
  
                    data = Data(edge_index=edge_index, edge_attr=edge_attr, x=x)
                    data.id = torch.tensor([count])
                    data.y = y
                    data.split = split[1]
                    # data.num_nodes = len(nodes_dict)
                    # data.api = file_name
                    data_list.append(data)
                    count += 1
        
        elif self.dataset in ["clone-detection"] :
            input_folder_path = ""
            for folder_path in self.raw_paths:
                if self.dataset in folder_path:
                    input_folder_path = folder_path
                    break
            print("\nProcessing: {}\n".format(input_folder_path))
            files = glob.glob(os.path.join(input_folder_path, '*.txt'))
            print("\nNumber of files: {}\n".format(len(files)))
            for file in tqdm.tqdm(files):
                try:
                    nodes_dict, edge_indices_FD, edge_indices_CD, edge_indices, edge_type, file_name, number_of_api_nodes = get_nodes_edges(file, add_reverse_edges = True, api_name = None)
                except Exception as e:
                    print("\nError: ", e)
                    continue
                    
                if(len(nodes_dict) == 0):
                    print("\nNo Data: ", file)
                    continue
                #print(nodes_dict, edge_indices_CD, edge_indices_FD, edge_type)

                # Node feature matrix with shape [num_nodes, num_node_features]=(N, 768).
                try:
                    with torch.no_grad():
                        CodeEmbedding = get_node_embedding_from_codet5p(nodes_dict)
                except Exception as e :
                    print("\nError: ", e)
                    print(nodes_dict)
                    continue
                #print(CodeEmbedding.shape)

                # FIXING DATA FOTMATS AND SHAPE
                x = CodeEmbedding.clone().detach()
                # print(x.shape)
  
                # data.y: Target to train against (may have arbitrary shape),
                # graph-level targets of shape [1, *]
                file_name = file[file.rindex("/", 0, len(file) - 1) + 1 : -4]
                id = int(file_name.split("_")[0])
                y = torch.tensor([1], dtype=torch.long)
                #print(type(y))

                # edge_index (LongTensor, optional) – Graph connectivity in COO format with shape [2, num_edges]
                edge_index_CD = torch.tensor(edge_indices_CD, dtype=torch.long).t().contiguous()
                edge_index_FD = torch.tensor(edge_indices_FD, dtype=torch.long).t().contiguous()
                edge_index = torch.tensor(edge_indices, dtype=torch.long).t().contiguous()
                edge_attr = torch.tensor(edge_type, dtype=torch.long).t().contiguous()
                #print(edge_index_CD, edge_index_FD, edge_index, edge_type)
  
                data = Data(edge_index=edge_index, edge_attr=edge_attr, x=x)
                data.id = torch.tensor([id])
                data.y = y
                # data.num_nodes = len(nodes_dict)
                # data.api = file_name
                data_list.append(data)

        else:
            raise ValueError('Invalid dataset name')

        data_list = [data.to(torch.device("cpu")) for data in data_list]
        
        if self.pre_filter is not None:
            data_list = [data for data in data_list if self.pre_filter(data)]

        if self.pre_transform is not None:
            data_list = [self.pre_transform(data) for data in data_list]

        data, slices = self.collate(data_list)
        torch.save((data, slices), self.processed_paths[0])

# NB: only properly tested when dataset_1 is chembl_with_labels and dataset_2
# is pcba_pretrain
def merge_dataset_objs(dataset_1, dataset_2):
    """
    Naively merge 2 molecule dataset objects, and ignore identities of
    molecules. Assumes both datasets have multiple y labels, and will pad
    accordingly. ie if dataset_1 has obj_1 with y dim 1310 and dataset_2 has
    obj_2 with y dim 128, then the resulting obj_1 and obj_2 will have dim
    1438, where obj_1 have the last 128 cols with 0, and obj_2 have
    the first 1310 cols with 0.
    :return: pytorch geometric dataset obj, with the x, edge_attr, edge_index,
    new y attributes only
    """
    d_1_y_dim = dataset_1[0].y.size()[0]
    d_2_y_dim = dataset_2[0].y.size()[0]

    data_list = []
    # keep only x, edge_attr, edge_index, padded_y then append
    for d in dataset_1:
        old_y = d.y
        new_y = torch.cat([old_y, torch.zeros(d_2_y_dim, dtype=torch.long)])
        data_list.append(Data(x=d.x, edge_index=d.edge_index,
                              edge_attr=d.edge_attr, y=new_y))

    for d in dataset_2:
        old_y = d.y
        new_y = torch.cat([torch.zeros(d_1_y_dim, dtype=torch.long), old_y.long()])
        data_list.append(Data(x=d.x, edge_index=d.edge_index,
                              edge_attr=d.edge_attr, y=new_y))

    # create 'empty' dataset obj. Just randomly pick a dataset and root path
    # that has already been processed
    new_dataset = MoleculeDataset(root='dataset/chembl_with_labels',
                                  dataset='chembl_with_labels', empty=True)
    # collate manually
    new_dataset.data, new_dataset.slices = new_dataset.collate(data_list)

    return new_dataset

def create_circular_fingerprint(mol, radius, size, chirality):
    """

    :param mol:
    :param radius:
    :param size:
    :param chirality:
    :return: np array of morgan fingerprint
    """
    fp = GetMorganFingerprintAsBitVect(mol, radius,
                                       nBits=size, useChirality=chirality)
    return np.array(fp)

class MoleculeFingerprintDataset(data.Dataset):
    def __init__(self, root, dataset, radius, size, chirality=True):
        """
        Create dataset object containing list of dicts, where each dict
        contains the circular fingerprint of the molecule, label, id,
        and possibly precomputed fold information
        :param root: directory of the dataset, containing a raw and
        processed_fp dir. The raw dir should contain the file containing the
        smiles, and the processed_fp dir can either be empty or a
        previously processed file
        :param dataset: name of dataset. Currently only implemented for
        tox21, hiv, chembl_with_labels
        :param radius: radius of the circular fingerprints
        :param size: size of the folded fingerprint vector
        :param chirality: if True, fingerprint includes chirality information
        """
        self.dataset = dataset
        self.root = root
        self.radius = radius
        self.size = size
        self.chirality = chirality

        self._load()

    def _process(self):
        data_smiles_list = []
        data_list = []
        if self.dataset == 'chembl_with_labels':
            smiles_list, rdkit_mol_objs, folds, labels = \
                _load_chembl_with_labels_dataset(os.path.join(self.root, 'raw'))
            print('processing')
            for i in range(len(rdkit_mol_objs)):
                print(i)
                rdkit_mol = rdkit_mol_objs[i]
                if rdkit_mol != None:
                    # # convert aromatic bonds to double bonds
                    fp_arr = create_circular_fingerprint(rdkit_mol,
                                                         self.radius,
                                                         self.size, self.chirality)
                    fp_arr = torch.tensor(fp_arr)
                    # manually add mol id
                    id = torch.tensor([i])  # id here is the index of the mol in
                    # the dataset
                    y = torch.tensor(labels[i, :])
                    # fold information
                    if i in folds[0]:
                        fold = torch.tensor([0])
                    elif i in folds[1]:
                        fold = torch.tensor([1])
                    else:
                        fold = torch.tensor([2])
                    data_list.append({'fp_arr': fp_arr, 'id': id, 'y': y,
                                      'fold': fold})
                    data_smiles_list.append(smiles_list[i])
        elif self.dataset == 'tox21':
            smiles_list, rdkit_mol_objs, labels = \
                _load_tox21_dataset(os.path.join(self.root, 'raw/tox21.csv'))
            print('processing')
            for i in range(len(smiles_list)):
                print(i)
                rdkit_mol = rdkit_mol_objs[i]
                ## convert aromatic bonds to double bonds
                fp_arr = create_circular_fingerprint(rdkit_mol,
                                                        self.radius,
                                                        self.size,
                                                        self.chirality)
                fp_arr = torch.tensor(fp_arr)

                # manually add mol id
                id = torch.tensor([i])  # id here is the index of the mol in
                # the dataset
                y = torch.tensor(labels[i, :])
                data_list.append({'fp_arr': fp_arr, 'id': id, 'y': y})
                data_smiles_list.append(smiles_list[i])
        elif self.dataset == 'hiv':
            smiles_list, rdkit_mol_objs, labels = \
                _load_hiv_dataset(os.path.join(self.root, 'raw/HIV.csv'))
            print('processing')
            for i in range(len(smiles_list)):
                print(i)
                rdkit_mol = rdkit_mol_objs[i]
                # # convert aromatic bonds to double bonds
                fp_arr = create_circular_fingerprint(rdkit_mol,
                                                        self.radius,
                                                        self.size,
                                                        self.chirality)
                fp_arr = torch.tensor(fp_arr)

                # manually add mol id
                id = torch.tensor([i])  # id here is the index of the mol in
                # the dataset
                y = torch.tensor([labels[i]])
                data_list.append({'fp_arr': fp_arr, 'id': id, 'y': y})
                data_smiles_list.append(smiles_list[i])
        else:
            raise ValueError('Invalid dataset name')

        # save processed data objects and smiles
        processed_dir = os.path.join(self.root, 'processed_fp')
        data_smiles_series = pd.Series(data_smiles_list)
        data_smiles_series.to_csv(os.path.join(processed_dir, 'smiles.csv'),
                                  index=False,
                                  header=False)
        with open(os.path.join(processed_dir,
                                    'fingerprint_data_processed.pkl'),
                  'wb') as f:
            pickle.dump(data_list, f)

    def _load(self):
        processed_dir = os.path.join(self.root, 'processed_fp')
        # check if saved file exist. If so, then load from save
        file_name_list = os.listdir(processed_dir)
        if 'fingerprint_data_processed.pkl' in file_name_list:
            with open(os.path.join(processed_dir,
                                   'fingerprint_data_processed.pkl'),
                      'rb') as f:
                self.data_list = pickle.load(f)
        # if no saved file exist, then perform processing steps, save then
        # reload
        else:
            self._process()
            self._load()

    def __len__(self):
        return len(self.data_list)

    def __getitem__(self, index):
        ## if iterable class is passed, return dataset objection
        if hasattr(index, "__iter__"):
            dataset = MoleculeFingerprintDataset(self.root, self.dataset, self.radius, self.size, chirality=self.chirality)
            dataset.data_list = [self.data_list[i] for i in index]
            return dataset
        else:
            return self.data_list[index]


def _load_tox21_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    tasks = ['NR-AR', 'NR-AR-LBD', 'NR-AhR', 'NR-Aromatase', 'NR-ER', 'NR-ER-LBD',
       'NR-PPAR-gamma', 'SR-ARE', 'SR-ATAD5', 'SR-HSE', 'SR-MMP', 'SR-p53']
    labels = input_df[tasks]
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # convert nan to 0
    labels = labels.fillna(0)
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.values

def _load_hiv_dataset(input_path):
    """
    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    labels = input_df['HIV_active']
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # there are no nans
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.values

def _load_bace_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array
    containing indices for each of the 3 folds, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['mol']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    labels = input_df['Class']
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # there are no nans
    folds = input_df['Model']
    folds = folds.replace('Train', 0)   # 0 -> train
    folds = folds.replace('Valid', 1)   # 1 -> valid
    folds = folds.replace('Test', 2)    # 2 -> test
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    assert len(smiles_list) == len(folds)
    return smiles_list, rdkit_mol_objs_list, folds.values, labels.values

def _load_bbbp_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]

    preprocessed_rdkit_mol_objs_list = [m if m != None else None for m in
                                                          rdkit_mol_objs_list]
    preprocessed_smiles_list = [AllChem.MolToSmiles(m) if m != None else
                                None for m in preprocessed_rdkit_mol_objs_list]
    labels = input_df['p_np']
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # there are no nans
    assert len(smiles_list) == len(preprocessed_rdkit_mol_objs_list)
    assert len(smiles_list) == len(preprocessed_smiles_list)
    assert len(smiles_list) == len(labels)
    return preprocessed_smiles_list, preprocessed_rdkit_mol_objs_list, \
           labels.values

def _load_clintox_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]

    preprocessed_rdkit_mol_objs_list = [m if m != None else None for m in
                                        rdkit_mol_objs_list]
    preprocessed_smiles_list = [AllChem.MolToSmiles(m) if m != None else
                                None for m in preprocessed_rdkit_mol_objs_list]
    tasks = ['FDA_APPROVED', 'CT_TOX']
    labels = input_df[tasks]
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # there are no nans
    assert len(smiles_list) == len(preprocessed_rdkit_mol_objs_list)
    assert len(smiles_list) == len(preprocessed_smiles_list)
    assert len(smiles_list) == len(labels)
    return preprocessed_smiles_list, preprocessed_rdkit_mol_objs_list, \
           labels.values
# input_path = 'dataset/clintox/raw/clintox.csv'
# smiles_list, rdkit_mol_objs_list, labels = _load_clintox_dataset(input_path)

def _load_esol_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels (regression task)
    """
    # NB: some examples have multiple species
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    labels = input_df['measured log solubility in mols per litre']
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.values
# input_path = 'dataset/esol/raw/delaney-processed.csv'
# smiles_list, rdkit_mol_objs_list, labels = _load_esol_dataset(input_path)

def _load_freesolv_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels (regression task)
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    labels = input_df['expt']
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.values


def _load_lipophilicity_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels (regression task)
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    labels = input_df['exp']
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.values


def _load_muv_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    tasks = ['MUV-466', 'MUV-548', 'MUV-600', 'MUV-644', 'MUV-652', 'MUV-689',
       'MUV-692', 'MUV-712', 'MUV-713', 'MUV-733', 'MUV-737', 'MUV-810',
       'MUV-832', 'MUV-846', 'MUV-852', 'MUV-858', 'MUV-859']
    labels = input_df[tasks]
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # convert nan to 0
    labels = labels.fillna(0)
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.values

def _load_sider_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    tasks = ['Hepatobiliary disorders',
       'Metabolism and nutrition disorders', 'Product issues', 'Eye disorders',
       'Investigations', 'Musculoskeletal and connective tissue disorders',
       'Gastrointestinal disorders', 'Social circumstances',
       'Immune system disorders', 'Reproductive system and breast disorders',
       'Neoplasms benign, malignant and unspecified (incl cysts and polyps)',
       'General disorders and administration site conditions',
       'Endocrine disorders', 'Surgical and medical procedures',
       'Vascular disorders', 'Blood and lymphatic system disorders',
       'Skin and subcutaneous tissue disorders',
       'Congenital, familial and genetic disorders',
       'Infections and infestations',
       'Respiratory, thoracic and mediastinal disorders',
       'Psychiatric disorders', 'Renal and urinary disorders',
       'Pregnancy, puerperium and perinatal conditions',
       'Ear and labyrinth disorders', 'Cardiac disorders',
       'Nervous system disorders',
       'Injury, poisoning and procedural complications']
    labels = input_df[tasks]
    # convert 0 to -1
    labels = labels.replace(0, -1)
    assert len(smiles_list) == len(rdkit_mol_objs_list)
    assert len(smiles_list) == len(labels)
    return smiles_list, rdkit_mol_objs_list, labels.value

def _load_toxcast_dataset(input_path):
    """

    :param input_path:
    :return: list of smiles, list of rdkit mol obj, np.array containing the
    labels
    """
    # NB: some examples have multiple species, some example smiles are invalid
    input_df = pd.read_csv(input_path, sep=',')
    smiles_list = input_df['smiles']
    rdkit_mol_objs_list = [AllChem.MolFromSmiles(s) for s in smiles_list]
    # Some smiles could not be successfully converted
    # to rdkit mol object so them to None
    preprocessed_rdkit_mol_objs_list = [m if m != None else None for m in
                                        rdkit_mol_objs_list]
    preprocessed_smiles_list = [AllChem.MolToSmiles(m) if m != None else
                                None for m in preprocessed_rdkit_mol_objs_list]
    tasks = list(input_df.columns)[1:]
    labels = input_df[tasks]
    # convert 0 to -1
    labels = labels.replace(0, -1)
    # convert nan to 0
    labels = labels.fillna(0)
    assert len(smiles_list) == len(preprocessed_rdkit_mol_objs_list)
    assert len(smiles_list) == len(preprocessed_smiles_list)
    assert len(smiles_list) == len(labels)
    return preprocessed_smiles_list, preprocessed_rdkit_mol_objs_list, \
           labels.values

def _load_chembl_with_labels_dataset(root_path):
    """
    Data from 'Large-scale comparison of machine learning methods for drug target prediction on ChEMBL'
    :param root_path: path to the folder containing the reduced chembl dataset
    :return: list of smiles, preprocessed rdkit mol obj list, list of np.array
    containing indices for each of the 3 folds, np.array containing the labels
    """
    # adapted from https://github.com/ml-jku/lsc/blob/master/pythonCode/lstm/loadData.py
    # first need to download the files and unzip:
    # wget http://bioinf.jku.at/research/lsc/chembl20/dataPythonReduced.zip
    # unzip and rename to chembl_with_labels
    # wget http://bioinf.jku.at/research/lsc/chembl20/dataPythonReduced/chembl20Smiles.pckl
    # into the dataPythonReduced directory
    # wget http://bioinf.jku.at/research/lsc/chembl20/dataPythonReduced/chembl20LSTM.pckl

    # 1. load folds and labels
    f=open(os.path.join(root_path, 'folds0.pckl'), 'rb')
    folds=pickle.load(f)
    f.close()

    f=open(os.path.join(root_path, 'labelsHard.pckl'), 'rb')
    targetMat=pickle.load(f)
    sampleAnnInd=pickle.load(f)
    targetAnnInd=pickle.load(f)
    f.close()

    targetMat=targetMat
    targetMat=targetMat.copy().tocsr()
    targetMat.sort_indices()
    targetAnnInd=targetAnnInd
    targetAnnInd=targetAnnInd-targetAnnInd.min()

    folds=[np.intersect1d(fold, sampleAnnInd.index.values).tolist() for fold in folds]
    targetMatTransposed=targetMat[sampleAnnInd[list(chain(*folds))]].T.tocsr()
    targetMatTransposed.sort_indices()
    # # num positive examples in each of the 1310 targets
    trainPosOverall=np.array([np.sum(targetMatTransposed[x].data > 0.5) for x in range(targetMatTransposed.shape[0])])
    # # num negative examples in each of the 1310 targets
    trainNegOverall=np.array([np.sum(targetMatTransposed[x].data < -0.5) for x in range(targetMatTransposed.shape[0])])
    # dense array containing the labels for the 456331 molecules and 1310 targets
    denseOutputData=targetMat.A # possible values are {-1, 0, 1}

    # 2. load structures
    f=open(os.path.join(root_path, 'chembl20LSTM.pckl'), 'rb')
    rdkitArr=pickle.load(f)
    f.close()

    assert len(rdkitArr) == denseOutputData.shape[0]
    assert len(rdkitArr) == len(folds[0]) + len(folds[1]) + len(folds[2])

    preprocessed_rdkitArr = []
    print('preprocessing')
    for i in range(len(rdkitArr)):
        print(i)
        m = rdkitArr[i]
        if m == None:
            preprocessed_rdkitArr.append(None)
        else:
            mol_species_list = split_rdkit_mol_obj(m)
            if len(mol_species_list) == 0:
                preprocessed_rdkitArr.append(None)
            else:
                largest_mol = get_largest_mol(mol_species_list)
                if len(largest_mol.GetAtoms()) <= 2:
                    preprocessed_rdkitArr.append(None)
                else:
                    preprocessed_rdkitArr.append(largest_mol)

    assert len(preprocessed_rdkitArr) == denseOutputData.shape[0]

    smiles_list = [AllChem.MolToSmiles(m) if m != None else None for m in
                   preprocessed_rdkitArr]   # bc some empty mol in the
    # rdkitArr zzz...

    assert len(preprocessed_rdkitArr) == len(smiles_list)

    return smiles_list, preprocessed_rdkitArr, folds, denseOutputData
# root_path = 'dataset/chembl_with_labels'

def check_smiles_validity(smiles):
    try:
        m = Chem.MolFromSmiles(smiles)
        if m:
            return True
        else:
            return False
    except:
        return False

def split_rdkit_mol_obj(mol):
    """
    Split rdkit mol object containing multiple species or one species into a
    list of mol objects or a list containing a single object respectively
    :param mol:
    :return:
    """
    smiles = AllChem.MolToSmiles(mol, isomericSmiles=True)
    smiles_list = smiles.split('.')
    mol_species_list = []
    for s in smiles_list:
        if check_smiles_validity(s):
            mol_species_list.append(AllChem.MolFromSmiles(s))
    return mol_species_list

def get_largest_mol(mol_list):
    """
    Given a list of rdkit mol objects, returns mol object containing the
    largest num of atoms. If multiple containing largest num of atoms,
    picks the first one
    :param mol_list:
    :return:
    """
    num_atoms_list = [len(m.GetAtoms()) for m in mol_list]
    largest_mol_idx = num_atoms_list.index(max(num_atoms_list))
    return mol_list[largest_mol_idx]

def create_all_datasets():
    #### create dataset
    downstream_dir = [
            'bace',
            'bbbp',
            'clintox',
            'esol',
            'freesolv',
            'hiv',
            'lipophilicity',
            'muv',
            'sider',
            'tox21',
            'toxcast'
            ]

    for dataset_name in downstream_dir:
        print(dataset_name)
        root = "dataset/" + dataset_name
        os.makedirs(root + "/processed", exist_ok=True)
        dataset = MoleculeDataset(root, dataset=dataset_name)
        print(dataset)


    dataset = MoleculeDataset(root = "dataset/chembl_filtered", dataset="chembl_filtered")
    print(dataset)
    dataset = MoleculeDataset(root = "dataset/zinc_standard_agent", dataset="zinc_standard_agent")
    print(dataset)


# test MoleculeDataset object
if __name__ == "__main__":

    create_all_datasets()

