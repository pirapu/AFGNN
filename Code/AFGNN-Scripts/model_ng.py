import torch
from torch_geometric.nn import MessagePassing
from torch_geometric.utils import add_self_loops, degree, softmax, remove_self_loops
from torch_geometric.nn import global_add_pool, global_mean_pool, global_max_pool, GlobalAttention, Set2Set
from torch_geometric.nn.aggr import AttentionalAggregation
import torch.nn.functional as F
from torch_scatter import scatter_add
from torch_geometric.nn.inits import glorot, zeros

from torch.nn import Linear, Sequential, BatchNorm1d, ReLU, Dropout
from torch_geometric.nn import GCNConv, GATConv, GATv2Conv, RGATConv, FastRGCNConv, GINEConv, GINConv

class CustomGeneraicGNN(torch.nn.Module):
    def __init__(self, num_layer = 3, emb_dim = 768, JK = "last", drop_ratio = 0, gnn_type = "gcn"):
        super(CustomGeneraicGNN, self).__init__()
        self.num_layer = num_layer
        self.drop_ratio = drop_ratio
        self.JK = JK
        self.gnn_type = gnn_type

        ###List of GNN Layers
        self.gnns = torch.nn.ModuleList()
        for layer in range(num_layer):
            if gnn_type in ["gcn", "gcnw"]:
                self.gnns.append(GCNConv(emb_dim, emb_dim))
            elif gnn_type == "gat":
                self.gnns.append(GATConv(emb_dim, emb_dim))
            elif gnn_type == "gatv2":
                self.gnns.append(GATv2Conv(emb_dim, emb_dim))
            elif gnn_type == "rgat":
                self.gnns.append(RGATConv(emb_dim, emb_dim, 3))
            elif gnn_type == "rgcn":
                self.gnns.append(FastRGCNConv(emb_dim, emb_dim, 3))
            elif gnn_type == "gin":
                self.gnns.append(GINConv(Sequential(Linear(emb_dim, emb_dim),
                                                     BatchNorm1d(emb_dim), 
                                                     ReLU(),
                                                     Linear(emb_dim, emb_dim), 
                                                     ReLU())))

        ###List of batchnorms
        self.batch_norms = torch.nn.ModuleList()
        for layer in range(num_layer):
            self.batch_norms.append(torch.nn.BatchNorm1d(emb_dim))

    def forward(self, x, edge_index, edge_type = None):
        #print(x.shape, edge_index.shape, edge_type.shape)
        #print(x[0])
        h_list = [x]
        for layer in range(self.num_layer):
            if self.gnn_type in ["rgat", "rgcn"]:
                h = self.gnns[layer](h_list[layer], edge_index, edge_type)
            elif self.gnn_type == "gcnw":
                for i in range(len(edge_type)):
                    edge_type[i] = 2.0 if edge_type[i] == 0 else 1.0
                h = self.gnns[layer](x = h_list[layer], edge_index = edge_index, edge_weight = edge_type.float())
            else:
                h = self.gnns[layer](x = h_list[layer], edge_index = edge_index)
            h = self.batch_norms[layer](h)
            if layer == self.num_layer - 1:
                #remove relu for the last layer
                h = F.dropout(h, self.drop_ratio, training = self.training)
            else:
                h = F.dropout(F.relu(h), self.drop_ratio, training = self.training)
            h_list.append(h)
            
        ### Different implementations of Jk-concat
        if self.JK == "concat":
            node_representation = torch.cat(h_list, dim = 1)
        elif self.JK == "last":
            node_representation = h_list[-1]
        elif self.JK == "max":
            h_list = [h.unsqueeze_(0) for h in h_list]
            node_representation = torch.max(torch.cat(h_list, dim = 0), dim = 0)[0]
        elif self.JK == "sum":
            h_list = [h.unsqueeze_(0) for h in h_list]
            node_representation = torch.sum(torch.cat(h_list, dim = 0), dim = 0)[0]

        return node_representation
        
class CustomGeneraicGNN_encoder(torch.nn.Module):
    def __init__(self, num_layer = 3, emb_dim = 768, JK = "last", drop_ratio = 0, gnn_type = "gcn", graph_pooling = "mean", num_tasks = None):
        super(CustomGeneraicGNN_encoder, self).__init__()
        self.num_layer = num_layer
        self.drop_ratio = drop_ratio
        self.JK = JK
        self.emb_dim = emb_dim
        self.num_tasks = num_tasks

        # Define the GNN model
        self.gnn = CustomGeneraicGNN(num_layer, emb_dim, JK, drop_ratio, gnn_type)
        
        # Different kind of graph pooling
        if graph_pooling == "sum":
            self.pool = global_add_pool
        elif graph_pooling == "mean":
            self.pool = global_mean_pool
        elif graph_pooling == "max":
            self.pool = global_max_pool
        elif graph_pooling == "attention":
            if self.JK == "concat":
                self.pool = AttentionalAggregation(gate_nn = torch.nn.Linear((self.num_layer + 1) * emb_dim, 1))
            else:
                self.pool = AttentionalAggregation(gate_nn = torch.nn.Linear(emb_dim, 1))
        elif graph_pooling[:-1] == "set2set":
            set2set_iter = int(graph_pooling[-1])
            if self.JK == "concat":
                self.pool = Set2Set((self.num_layer + 1) * emb_dim, set2set_iter)
            else:
                self.pool = Set2Set(emb_dim, set2set_iter)
        else:
            raise ValueError("Invalid graph pooling type.")
        
        #For graph-level binary classification
        if graph_pooling[:-1] == "set2set":
            self.mult = 2
        else:
            self.mult = 1
        
        if self.num_tasks != None:
            if self.JK == "concat":
                self.graph_pred_linear = torch.nn.Linear(self.mult * (self.num_layer + 1) * self.emb_dim, self.num_tasks)
            else:
                self.graph_pred_linear = torch.nn.Linear(self.mult * self.emb_dim, self.num_tasks)

    def from_pretrained(self, model_file):
        self.gnn.load_state_dict(torch.load(model_file))

    def forward(self, x, edge_index, edge_type = None, batch = None, classify = False):
        node_representation = self.gnn(x, edge_index, edge_type)
        graph_representation = self.pool(node_representation, batch)
        if classify:
            return self.graph_pred_linear(graph_representation)
        else:
            return graph_representation