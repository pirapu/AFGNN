public class func{
public void setDBContextClassLoader(String jarName){
    String root = System.getProperty("derby.system.home", System.getProperty("user.dir"));
    File jar = new File(root, jarName);
    URLClassLoader cl = new URLClassLoader(new URL[] {jar.toURI().toURL()});
}
}
