public class func{
public void addClasses(WebArchive war,String clazz,ClassLoader cl){
            Class<?> current = cl.loadClass(clazz);
            addClasses(war, current);
}
}
