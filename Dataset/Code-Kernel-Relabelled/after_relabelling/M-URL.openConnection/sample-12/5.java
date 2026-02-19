public class func{
public void readJarDirectoryEntries(URL location,String basePath,Set<String> resources){
        JarURLConnection conn = (JarURLConnection) location.openConnection();
        jarfile = conn.getJarFile();
        Enumeration<JarEntry> entries = jarfile.entries();
        while (entries != null && entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if (entry.isDirectory() && StringUtils.startsWith(name, basePath)) {
                resources.add(name);
            }
        }
}
}
