public class func{
public void _readJalopyXmlFromClassLoader(){
    ClassLoader classLoader = ServiceBuilder.class.getClassLoader();
    URL url = classLoader.getResource("jalopy.xml");
    if (url == null) {
      throw new RuntimeException(
        "Unable to load jalopy.xml from the class loader");
    }
}
}
