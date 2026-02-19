public class func{
public void getResourceURL(String resource,Class<?> c){
        if (c != null) {
            ClassLoader classLoader = c.getClassLoader();
            if (classLoader != null) {
                return classLoader.getResource(resource);
            }
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader.getResource(resource);
        }
        return ClassLoader.getSystemResource(resource);
}
}
