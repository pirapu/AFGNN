public class func{
public void tryLoadClass(String className,ClassLoader classLoader){
        if (className.startsWith("[")) {
            return Class.forName(className, false, classLoader);
        } else {
            return classLoader.loadClass(className);
        }
}
}
