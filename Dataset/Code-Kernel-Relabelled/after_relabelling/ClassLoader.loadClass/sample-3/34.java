public class func{
public void classIsPresent(String className,ClassLoader classLoader){
      if (classLoader == null) {
        Class.forName(className);
      } else {
        classLoader.loadClass(className);
      }
}
}
