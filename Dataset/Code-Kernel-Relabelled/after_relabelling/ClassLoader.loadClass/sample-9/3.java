public class func{
public void loadClass(String className){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        c = classLoader.loadClass(className);
}
}
