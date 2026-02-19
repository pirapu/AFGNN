public class func{
public void getClasspathForResource(ClassLoader classLoader,String name){
        if (classLoader == null) {
            return getClasspathForResource(ClassLoader.getSystemResource(name), name);
        } else {
            return getClasspathForResource(classLoader.getResource(name), name);
        }
}
}
