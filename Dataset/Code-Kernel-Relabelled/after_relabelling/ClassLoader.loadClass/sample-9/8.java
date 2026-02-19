public class func{
public void newInstance(String clazz){
        ClassLoader cl = Thread.currentThread().getContextClassLoader ();
        Class type = cl.loadClass (clazz);
        return type.newInstance ();
}
}
