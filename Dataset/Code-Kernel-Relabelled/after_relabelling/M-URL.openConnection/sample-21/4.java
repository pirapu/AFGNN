public class func{
public void createJarInputStream(){
        JarURLConnection jarConn = (JarURLConnection) url.openConnection();
        URL resourceURL = jarConn.getJarFileURL();
        URLConnection resourceConn = resourceURL.openConnection();
        resourceConn.setUseCaches(false);
        return new NonClosingJarInputStream(resourceConn.getInputStream());
}
}
