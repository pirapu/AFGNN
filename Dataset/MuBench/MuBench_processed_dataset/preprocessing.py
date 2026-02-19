import sys
import json
import pandas as pd
import random
import os
from pathlib import Path
import re
import glob
import tqdm
import pyparsing

def copyFile(input_file_path, output_file_path):
    
    input_file = open(input_file_path, "r")
    newfile = open(output_file_path, "w+")
    
    # Remove all comments
    original_code = input_file.readlines()
    
    # Add dummy class
    newfile.write("public class func{\n")

    for line in original_code:
        line = line.replace('\u00A0', " ")
        
        # Skip empty lines
        if line.strip() == "" or line.strip().replace("\n", "").replace("\t", "").strip() == "":
            continue
        
        # Remove import statements, packages, @test, comments
        if line.strip().startswith("//")  or line.strip().startswith("import") or line.strip().startswith("package") or line.strip().startswith("@"):
            continue
        
        # Remove inline comments
        if "//" in line and "://" not in line:
            line = line[:line.index("//")].rstrip() + "\n"
        
        # Add newline at the end
        if not line.endswith("\n"):
            line += "\n"
        newfile.write(line)
    newfile.write("}\n")
    
    newfile.close()
    input_file.close()


misuse_folder_location = "/u/student/2021/cs21mtech12001/API-Minsuse/Repository/Benchmarks/MuBench/mubench_methods_misuse"
correct_usages_folder_location = "/u/student/2021/cs21mtech12001/API-Minsuse/Repository/Benchmarks/MuBench/mubench_methods_correct_usage"
output_folder_location = "/u/student/2021/cs21mtech12001/API-Minsuse/Repository/Benchmarks/MuBench/after_processing"

for input_folder_location in [misuse_folder_location, correct_usages_folder_location]:
    folders = glob.glob(input_folder_location + "/*/")
    for folder in tqdm.tqdm(folders):
        folder = folder[folder.rindex("/", 0, len(folder) - 1) + 1 : -1]
        input_project_folder = input_folder_location + "/" + folder
        java_files = glob.glob(os.path.join(input_project_folder, '*.java'))
        print(folder, ": ", len(java_files))
    
        output_project_folder_location = output_folder_location + "/" + folder
        if not os.path.exists(output_folder_location):
            os.mkdir(output_folder_location)
        if not os.path.exists(output_project_folder_location):
            os.mkdir(output_project_folder_location)
        
        for input_java_file_path in java_files:
            file_name = input_java_file_path[input_java_file_path.rindex("/") + 1:-5]
            if input_folder_location == misuse_folder_location:
                file_name += "_1"
            else:
                file_name += "_0"
            output_java_file_path = output_project_folder_location + "/" + file_name + ".java"
            copyFile(input_java_file_path, output_java_file_path)
        