public class func{
public void parseDistributedCacheArtifacts(Configuration conf,Map<String,LocalResource> localResources,LocalResourceType type,URI[] uris,long[] timestamps,long[] sizes,boolean visibilities[]){
      if ((uris.length != timestamps.length) || (uris.length != sizes.length) ||
          (uris.length != visibilities.length)) {
        throw new IllegalArgumentException("Invalid specification for " +
            "distributed-cache artifacts of type " + type + " :" +
            " #uris=" + uris.length +
            " #timestamps=" + timestamps.length +
            " #visibilities=" + visibilities.length
            );
      }
      for (int i = 0; i < uris.length; ++i) {
        URI u = uris[i];
        Path p = new Path(u);
        FileSystem remoteFS = p.getFileSystem(conf);
        p = remoteFS.resolvePath(p.makeQualified(remoteFS.getUri(),
            remoteFS.getWorkingDirectory()));
        Path name = new Path((null == u.getFragment())
          ? p.getName()
          : u.getFragment());
        if (name.isAbsolute()) {
          throw new IllegalArgumentException("Resource name must be relative");
        }
        String linkName = name.toUri().getPath();
        LocalResource orig = localResources.get(linkName);
        if(orig != null && !orig.getResource().equals(
            ConverterUtils.getYarnUrlFromURI(p.toUri()))) {
          throw new InvalidJobConfException(
              getResourceDescription(orig.getType()) + orig.getResource() + 
              " conflicts with " + getResourceDescription(type) + u);
        }
        localResources.put(linkName, LocalResource.newInstance(ConverterUtils
          .getYarnUrlFromURI(p.toUri()), type, visibilities[i]
            ? LocalResourceVisibility.PUBLIC : LocalResourceVisibility.PRIVATE,
          sizes[i], timestamps[i]));
      }
}
}
