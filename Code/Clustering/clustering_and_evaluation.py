import numpy as np

from sklearn.cluster import KMeans
from sklearn.cluster import Birch
from sklearn.mixture import GaussianMixture
from sklearn.cluster import AgglomerativeClustering

from sklearn.metrics import silhouette_score
from sklearn.metrics import davies_bouldin_score
from sklearn.metrics import calinski_harabasz_score
from sklearn.metrics.cluster import v_measure_score
from sklearn.metrics.cluster import adjusted_rand_score
from sklearn.metrics.cluster import fowlkes_mallows_score
from sklearn.metrics.cluster import adjusted_mutual_info_score

### Cluster the embeddings
def cluster_embeddings(embeddings, cluster_no, clustering_algorithm = "Birch"):
    if(clustering_algorithm == "Birch"):
        if cluster_no == 0 or cluster_no == None:
            birch_model = Birch(n_clusters = None, threshold = 3.0)
        else:
            birch_model = Birch(n_clusters = cluster_no, threshold = 3.0)
        clusters_result = birch_model.fit_predict(embeddings)
    elif(clustering_algorithm == "Agglomerative"):
        agglomerative_model = AgglomerativeClustering(n_clusters = cluster_no)
        clusters_result = agglomerative_model.fit_predict(embeddings)
    elif(clustering_algorithm == "KMeans"):
        kmeans_model = KMeans(n_clusters = cluster_no)
        clusters_result = kmeans_model.fit_predict(embeddings)
    elif(clustering_algorithm == "GM"):
        gaussian_model = GaussianMixture(n_components = cluster_no)
        clusters_result = gaussian_model.fit_predict(embeddings)
    return clusters_result

### Evaluate the clusters using a varity of Internal and External validation metric
def evaluate_clusters(embeddings, cluster_labels, ground_truth_labels = None):
        
    if len(set(cluster_labels)) == 1 or len(set(cluster_labels)) == len(embeddings):
        print("Silhouette, Davies-Bouldin and Calinski-Harabasz score is not possible with 1 or N clusters")
        silhouette_result = None
        davies_bouldin_result = None
        calinski_harabasz_result = None
    else:
        silhouette_result = silhouette_score(embeddings, cluster_labels)
        davies_bouldin_result = davies_bouldin_score(embeddings, cluster_labels)
        calinski_harabasz_result = calinski_harabasz_score(embeddings, cluster_labels)
    
    if(ground_truth_labels != None):
        adjusted_rand_result = adjusted_rand_score(ground_truth_labels, cluster_labels)
        fowlkes_mallows_result = fowlkes_mallows_score(ground_truth_labels, cluster_labels)
        adjusted_mutual_info_result = adjusted_mutual_info_score(ground_truth_labels, cluster_labels)
        v_measure_result = v_measure_score(ground_truth_labels, cluster_labels)
    else:
        adjusted_rand_result, fowlkes_mallows_result, adjusted_mutual_info_result, v_measure_result = None, None, None, None
    
    return silhouette_result, davies_bouldin_result, calinski_harabasz_result, adjusted_rand_result, fowlkes_mallows_result, adjusted_mutual_info_result, v_measure_result
    
### Find optimal cluster number via Internal validation
def find_best_cluster_count(embeddings, minimum_cluster_no = 1, maximum_cluster_no = 10, clustering_algorithm = "Birch", internal_index = "DB"):
    best_silhouette_result, best_silhouette_cluster = None, len(embeddings)
    best_davies_bouldin_result, best_davies_bouldin_cluster = None, len(embeddings)
    best_calinski_harabasz_result, best_calinski_harabasz_cluster = None, len(embeddings)
    for cluster_no in range(minimum_cluster_no, maximum_cluster_no + 1):
        try:
            cluster_labels = cluster_embeddings(embeddings, cluster_no, clustering_algorithm)
        except Exception as e:
            print("Exception: ({}) for cluster number: {}".format(e, cluster_no))
            continue
        silhouette_result, davies_bouldin_result, calinski_harabasz_result, _, _, _, _ = evaluate_clusters(embeddings, cluster_labels)
        if (silhouette_result != None and best_silhouette_result != None and silhouette_result > best_silhouette_result) or (silhouette_result != None and best_silhouette_result == None):
            best_silhouette_result = silhouette_result
            best_silhouette_cluster = cluster_no
            
        if (davies_bouldin_result != None and best_davies_bouldin_result != None and davies_bouldin_result < best_davies_bouldin_result) or (davies_bouldin_result != None and best_davies_bouldin_result == None):
            best_davies_bouldin_result = davies_bouldin_result
            best_davies_bouldin_cluster = cluster_no
        
        if (calinski_harabasz_result != None and best_calinski_harabasz_result != None and calinski_harabasz_result > best_calinski_harabasz_result) or (calinski_harabasz_result != None and best_calinski_harabasz_result == None):
            best_calinski_harabasz_result = calinski_harabasz_result
            best_calinski_harabasz_cluster = cluster_no
            
    print("Best silhouette score: {} for cluster count: {}".format(round(best_silhouette_result, 3) if best_silhouette_result != None else best_silhouette_result, best_silhouette_cluster))
    print("Best davies_bouldin score: {} for cluster count: {}".format(round(best_davies_bouldin_result, 3) if best_davies_bouldin_result != None else best_davies_bouldin_result, best_davies_bouldin_cluster))
    print("Best calinski_harabasz score: {} for cluster count: {}".format(round(best_calinski_harabasz_result, 3) if best_calinski_harabasz_result != None else best_calinski_harabasz_result, best_calinski_harabasz_cluster))
    
    if internal_index == "DB":
        average_best_cluster_count = best_davies_bouldin_cluster
    elif internal_index == "SH":
        average_best_cluster_count = best_silhouette_cluster
    elif internal_index == "CH":
        average_best_cluster_count = best_calinski_harabasz_cluster
    #average_best_cluster_count = int(round((best_silhouette_cluster + best_davies_bouldin_cluster)/2.0, 0))
    print("Average best cluster number: {} using {} score".format(average_best_cluster_count, internal_index))
    return average_best_cluster_count

### Find best cluster number and run evaluations
def find_and_evaluate_best_clustering(embeddings, only_file_names = None, ground_truth_labels = None, clustering_algorithm = "Birch", internal_index = "DB"):
    
    average_best_cluster_count = find_best_cluster_count(embeddings, 1, len(embeddings), clustering_algorithm, internal_index)
    print("\nClustering for cluster number: ", average_best_cluster_count)
    
    clusters_result = cluster_embeddings(embeddings, average_best_cluster_count, clustering_algorithm)
    cluster_count = {}
    cluster_to_file_mapping = {}
    cluster_to_emb_mapping = {}
    for i in range(len(clusters_result)):
        try:
            cluster_count[clusters_result[i]] += 1
            cluster_to_emb_mapping[clusters_result[i]].append(embeddings[i])
            if only_file_names != None:
                cluster_to_file_mapping[clusters_result[i]].append(only_file_names[i])
        except:
            cluster_count[clusters_result[i]] = 1
            cluster_to_emb_mapping[clusters_result[i]] = [embeddings[i]]
            if only_file_names != None:
                cluster_to_file_mapping[clusters_result[i]] = [only_file_names[i]]
    cluster_count = dict(sorted(cluster_count.items(), key=lambda item: item[1], reverse = True))
    cluster_to_file_mapping = dict(sorted(cluster_to_file_mapping.items(), key=lambda item: len(item[1]), reverse = True))
    print("Cluster Counts: ", cluster_count)
    print("Cluster Mapping: ", cluster_to_file_mapping)
    
    # Find cluster centers
    cluster_centers = {}
    for cluster in cluster_to_file_mapping:
        min_dis_ind = find_cluster_center(cluster_to_emb_mapping[cluster])
        file_name = cluster_to_file_mapping[cluster][min_dis_ind]
        cluster_centers[cluster] = file_name
    print("Cluster Centers: ", cluster_centers)
    
    silhouette_result, davies_bouldin_result, calinski_harabasz_result, adjusted_rand_result, fowlkes_mallows_result, adjusted_mutual_info_result, v_measure_result = evaluate_clusters(embeddings, clusters_result, ground_truth_labels)
    print("\nsilhouette_result: ", round(silhouette_result, 3) if silhouette_result != None else silhouette_result)
    print("davies_bouldin_result: ", round(davies_bouldin_result, 3) if davies_bouldin_result != None else davies_bouldin_result)
    #print("calinski_harabasz_result: ", calinski_harabasz_result)
    print("\nadjusted_rand_result: ", round(adjusted_rand_result, 3))
    #print("fowlkes_mallows_result: ", fowlkes_mallows_result)
    print("adjusted_mutual_info_result: ", round(adjusted_mutual_info_result, 3))
    #print("v_measure_result: ", v_measure_result)
    return silhouette_result, davies_bouldin_result, calinski_harabasz_result, adjusted_rand_result, fowlkes_mallows_result, adjusted_mutual_info_result, v_measure_result, average_best_cluster_count, cluster_count

### Find cluster center using Eucledian distance
def find_cluster_center(embeddings):
    if len(embeddings) == 1:
        return 0
    average_dist = dict.fromkeys(range(len(embeddings)), 0)
    for i in range(len(embeddings)):
        for j in range(i+1, len(embeddings)):
            dist = np.linalg.norm(embeddings[i] - embeddings[j])
            average_dist[i] += dist
            average_dist[j] += dist
    min_dist, min_dis_ind = np.inf, 0
    for i in range(len(embeddings)):
        average_dist[i] /= (len(embeddings) - 1)
        if average_dist[i] < min_dist:
            min_dis_ind = i
            min_dist = average_dist[i]
    return min_dis_ind
    
### Implementation to calculate Dunn Index (Internal)
def calculate_dunn_index(clusters):
    """Calculates the Dunn index for a given set of clusters.

    Args:
        clusters: A list of lists, where each sublist represents a cluster.

    Returns:
        The Dunn index, a float.
    """

    # Convert cluster points to numpy arrays
    clusters = [np.array(cluster) for cluster in clusters]

    # Calculate the minimum inter-cluster distance.
    min_inter_cluster_distance = float('inf')
    for cluster1 in clusters:
        for cluster2 in clusters:
            if np.array_equal(cluster1, cluster2):
                continue
            inter_cluster_distance = np.min([np.linalg.norm(point1 - point2) for point1 in cluster1 for point2 in cluster2])
            min_inter_cluster_distance = min(min_inter_cluster_distance, inter_cluster_distance)

    # Calculate the maximum intra-cluster distance.
    max_intra_cluster_distance = 0.0
    for cluster in clusters:
        intra_cluster_distance = np.max([np.linalg.norm(point1 - point2) for point1 in cluster for point2 in cluster])
        max_intra_cluster_distance = max(max_intra_cluster_distance, intra_cluster_distance)

    # Calculate the Dunn index.
    dunn_index = min_inter_cluster_distance / max_intra_cluster_distance
    return dunn_index