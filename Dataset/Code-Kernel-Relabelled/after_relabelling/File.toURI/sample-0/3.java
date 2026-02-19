public class func{
public void generateBuildPath(List<File> systemDependencies){
        for (File systemDependency : systemDependencies) {
            try {
                urls.add(systemDependency.toURI().toURL());
            } catch (MalformedURLException e) {
            }
        }
}
}
