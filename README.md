# AFGNN: API Misuse Detection using Graph Neural Networks and Clustering

APIs are essential for software development, enabling code reuse and improving efficiency, but reliance on resources like Stack Overflow and LLMs can lead to unreliable usage and vulnerabilities.

This artifact presents `AFGNN`, a novel framework for efficiently detecting API misuse in Java code. The approach leverages a `Graph Neural Network (GNN)` model to generate embeddings of Java API usage code using a custom `API Flow Graph (AFG)` representation. This representation captures execution sequences, data flow, and control flow, enabling a better understanding of API usage patterns. AFGNN employs self-supervised pre-training and clustering to analyze API usage and identify potential misuse.

Our evaluation demonstrates that `AFGNN` outperforms state-of-the-art LLMs and other misuse detectors in efficiency and accuracy on real-world dataset.

---

## Directory Structure
```
──AFGNN/
    ├── Code/                # Code for training and evaluation
    │       ├── AFG-Generator
    │       ├── AFG-Pruning
    │       ├── Clustering
    │       ├── Evaluation-Scripts
    │       ├── AFGNN-Scripts
    │       └── AFGNN-Trained-Weights 
    ├── Dataset/             # Input datasets for training and evaluation
    │       ├── Code-Kernel-Relabelled
    │       └── MuBench 
    ├── Set_of_rules_for_labeling.pdf
    └── README.md
```
---
## Prerequisites

 * Python - 3.8 or higher 
 * Java Development Kit (JDK) - 11 or higher
 * PyTorch - 1.11 or higher
 * PyTorch Geometric - 1.7.0

## Dataset

### Code-Kernel_Relabelled

As mentioned in the paper, we have relabelled the Code-Kernel data for the clustering evaluation (RQ1 task), and that data is available under the `./Dataset/Code-Kernel-Relabelled/after_relabelling` folder.

To utilize the data, we need to:
1. Preprocess it using `./Dataset/Code-Kernel-Relabelled/preprocessing.py`
2. Generate AFGs using `./Code/AFG-Generator/COMP_project/src/main/java/pdg_gui/mainframe.java`
3. Prune the AFGs using `./Code/AFG-Pruning/afg_pruning.py`

Finally, the generated AFGs can be used for evaluation with AFGNN against the baselines using the `./Code/Evaluation-Scripts/test-with-codekernel-relabeled-clustering-data.ipynb` script.

---

### MuBench

Due to the dataset's age (six years old), some project repositories were inaccessible, limiting our access to misuse examples. We collected all correct usage examples, available misuse examples, and YML files, and generated the unavailable misuse examples based on the YML information.

The `mubench_java_file_downloader.py` script is provided to prepare the MuBench dataset. Before executing the script, update:
- `root_folder` variable on **line 57** → `root_folder = './API_Misuse/MUBench'`
- `output_folder` variable → set to your desired output path

We also provide the prepared dataset in the `MuBench_processed_dataset` folder, which can be used directly for experiments. Follow the folder structure specified in the path statements of the `test-with-MuBench-examples-with-AFGNN.ipynb` notebook to arrange the collected Java files. Follow the same AFG generation process explained above.

Finally, the generated AFGs can be used for misuse detection evaluation with:
- AFGNN → `./Code/Evaluation-Scripts/test-with-MuBench-examples-with-AFGNN.ipynb`
- GraphCodeBERT → `./Code/Evaluation-Scripts/test-with-MuBench-examples-with-GraphCodeBERT.ipynb`

## Usage Instructions

## Training AFGNN

* **Generate API Flow Graphs (AFGs):**
Execute the AFG-Generator project on your data to generate AFG.
```
cd COMP_project/
mvn clean install
mvn exec:java -Dexec.mainClass=pdg_gui.mainframe 
```

* **Train the AFGNN model on your pruned AFG data:**
```
python ./Code/AFGNN-Scripts/pretrain_contextpred.py
```

## Evaluation
Run the following Jupyter Notebook files to perform the respective evaluations. We have provided the pre-trained weights for the best GCN and RGCN version of AFGNN (`./Code/AFGNN-Trained-Weights`) that can be directly used for evaluation.

*  **Effectiveness of AFGNN in API Usage Clustering:**
```
test-with-codekernel-relabeled-clustering-data.ipynb
```

*  **Identifying the API misuse in the MUBench dataset using AFGNN:**
```
test-with-MuBench-examples-with-AFGNN.ipynb
```

