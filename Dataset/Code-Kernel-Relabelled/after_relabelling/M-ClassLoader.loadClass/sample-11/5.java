public class func{
public void loadClasses(ClassLoader classLoader,Set<String> classNames){
    for(String className : classNames) {
      classes.add(classLoader.loadClass(className));
    }
}
}
