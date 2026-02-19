public class func{
public void loadClass(String clazz,Context context){
    ClassLoader cl = getClassLoaderOfObject(context);
    return cl.loadClass(clazz);
}
}
