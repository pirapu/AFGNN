public class func{
public void resourceOnClasspath(final String filename){
        ClassLoader cldr = Thread.currentThread().getContextClassLoader();
        return cldr.getResource(filename);
}
}
