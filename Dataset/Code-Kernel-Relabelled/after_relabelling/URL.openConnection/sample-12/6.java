public class func{
public void resolveModuleEntriesFromJar(URL url,String _prefix){
        JarURLConnection conn=(JarURLConnection)url.openConnection();
        Enumeration entries=conn.getJarFile().entries();
        while (entries.hasMoreElements())
        {
            JarEntry entry = (JarEntry) entries.nextElement();
            String name=entry.getName();
            if(name.startsWith(prefix) && !entry.isDirectory())
            {
                resourceList.add(name);
            }
        }
}
}
