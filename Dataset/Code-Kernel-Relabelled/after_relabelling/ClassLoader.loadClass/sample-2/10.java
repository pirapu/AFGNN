public class func{
public void loadClasses(ClassLoader classLoader,String[] names){
    for (int i = 0; i < names.length; i++) {
      classes[i] = classLoader.loadClass(names[i]);
    }
}
}
