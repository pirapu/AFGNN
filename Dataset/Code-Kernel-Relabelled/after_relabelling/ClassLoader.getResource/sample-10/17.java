public class func{
public void getURL(final String name){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(name);
}
}
