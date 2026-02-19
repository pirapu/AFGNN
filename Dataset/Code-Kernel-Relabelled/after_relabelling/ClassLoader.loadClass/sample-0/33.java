public class func{
public void delegateToParent(String classname){
        ClassLoader cl = getParent();
        if (cl != null)
            return cl.loadClass(classname);
        else
            return findSystemClass(classname);
}
}
