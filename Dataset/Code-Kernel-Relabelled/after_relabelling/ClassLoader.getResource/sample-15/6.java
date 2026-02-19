public class func{
public void uri(String name){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return cl.getResource(name).toString();
}
}
