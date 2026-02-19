public class func{
public void findCurrentResourceVersion(String resourceUrl){
        ClassLoader cl = getClass().getClassLoader();
        return cl.getResource(resourceUrl);
}
}
