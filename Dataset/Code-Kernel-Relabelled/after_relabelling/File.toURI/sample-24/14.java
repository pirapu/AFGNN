public class func{
public void createFallbackClassLoader(Collection<File> files){
        List<URL> urls = new ArrayList<URL>(files.size());
        for (File file : files) {
            urls.add(file.toURI().toURL());
        }
        return new URLClassLoader(urls.toArray(new URL[urls.size()]), null);
}
}
