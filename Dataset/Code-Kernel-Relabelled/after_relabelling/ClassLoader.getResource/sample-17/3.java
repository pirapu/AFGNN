public class func{
public void findJarDirectory(String name){
    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource(name);
    assertNotNull("Expecting on the classpath: " + name);
}
}
