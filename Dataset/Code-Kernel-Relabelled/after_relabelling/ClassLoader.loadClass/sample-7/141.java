public class func{
public void getFileFromJBossVfsURL(URL url,ClassLoader loader){
        Class<?> virtualFileClass = loader.loadClass("org.jboss.vfs.VirtualFile");
        Method getNameMethod = Reflections.getMethod(virtualFileClass, "getName");
        File physicalFile = (File) Reflections.invoke(getPhysicalFileMethod, virtualFile);
        checkNotNull(physicalFile, "org.jboss.vfs.VirtualFile.getPhysicalFile() returned null");
        String name = (String) Reflections.invoke(getNameMethod, virtualFile);
        checkNotNull(name, "org.jboss.vfs.VirtualFile.getName() returned null");
}
}
