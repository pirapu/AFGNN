import os
import yaml
import requests
'''
Bfore executing this script: 
    clone the MuBENCH project 
    and then execute the script on the clonned repository
'''
def find_yml_files(root_dir):
    """
    Recursively find all YML files in the given root directory.
    """
    yml_files = []
    for root, _, files in os.walk(root_dir):
        for file in files:
            if file.endswith(('.yml', '.yaml')):
                yml_files.append(os.path.join(root, file))
    return yml_files

def extract_commit_url(yml_file):
    """
    Extract the commit URL from a YML file.
    """
    with open(yml_file, 'r', encoding='utf-8') as file:
        try:
            content = yaml.safe_load(file)
            if 'fix' in content and 'commit' in content['fix']:
                return content['fix']['commit']
        except yaml.YAMLError as e:
            print(f"Error reading {yml_file}: {e}")
    return None

def download_file_from_github(commit_url, output_folder):
    """
    Download the file from the GitHub commit URL.
    """
    try:
        # Extract the raw file URL from the commit URL
        raw_url = commit_url.replace("https://github.com/", "https://raw.githubusercontent.com/").replace("/commit/", "/")
        response = requests.get(raw_url, timeout=10)
        if response.status_code == 200:
            # Extract the filename from the URL
            filename = raw_url.split('/')[-1]
            output_path = os.path.join(output_folder, filename)
            with open(output_path, 'wb') as file:
                file.write(response.content)
            print(f"Downloaded: {output_path}")
        else:
            print(f"Failed to download {commit_url}: HTTP {response.status_code}")
    except Exception as e:
        print(f"Error downloading {commit_url}: {e}")

def main():
    """
    Main function to find YML files, extract commit URLs, and download files.
    """
    root_folder = './API_Misuse/MUBench/data'
    output_folder = './API_Misuse/MUBench/new_output'
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)

    yml_files = find_yml_files(root_folder)
    for yml_file in yml_files:
        commit_url = extract_commit_url(yml_file)
        if commit_url:
            download_file_from_github(commit_url, output_folder)
        else:
            print(f"No commit URL found in {yml_file}")

if __name__ == "__main__":
    main()