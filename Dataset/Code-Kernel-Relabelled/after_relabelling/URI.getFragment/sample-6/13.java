public class func{
public void parseDistributedCacheArtifacts(Configuration conf,ApplicationSubmissionContext container,LocalResourceType type,URI[] uris,long[] timestamps,long[] sizes,boolean visibilities[],Path[] pathsToPutOnClasspath){
      for (int i = 0; i < uris.length; ++i) {
        URI u = uris[i];
        Path p = new Path(u);
        FileSystem fs = p.getFileSystem(conf);
        p = fs.resolvePath(
            p.makeQualified(fs.getUri(), fs.getWorkingDirectory()));
        Path name = new Path((null == u.getFragment())
          ? p.getName()
          : u.getFragment());
        if (name.isAbsolute()) {
          throw new IllegalArgumentException("Resource name must be relative");
        }
        String linkName = name.toUri().getPath();
        container.setResourceTodo(
            linkName,
            createLocalResource(
                p.toUri(), type, 
                visibilities[i]
                  ? LocalResourceVisibility.PUBLIC
                  : LocalResourceVisibility.PRIVATE,
                sizes[i], timestamps[i])
        );
        if (classPaths.containsKey(u.getPath())) {
          Map<String, String> environment = container.getAllEnvironment();
          MRApps.addToClassPath(environment, linkName);
        }
      }
}
}
