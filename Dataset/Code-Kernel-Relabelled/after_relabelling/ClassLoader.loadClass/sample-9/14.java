public class func{
public void loadWithCustomClassLoader(String className){
      URL thisClass = this.getClass().getResource(this.getClass().getSimpleName() + ".class");
      ClassLoader customClassLoader = new URLClassLoader(new URL[] {thisClass});
      Class<?> customInstanceFromDifferentClassLoader = customClassLoader.loadClass(className);
      return customInstanceFromDifferentClassLoader.newInstance();
}
}
