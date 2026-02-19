public class func{
public void uploadFiles(Job job,Path submitJobDir){
        Path tmp = new Path(tmpURI);
        Path newPath = copyRemoteFiles(archivesDir, tmp, conf, replication);
          URI pathURI = getPathURI(newPath, tmpURI.getFragment());
          DistributedCache.addCacheArchive(pathURI, conf);
          throw new IOException("Failed to create uri for " + tmpArchives, ue);
}
}
