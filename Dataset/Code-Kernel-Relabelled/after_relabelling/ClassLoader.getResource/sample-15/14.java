public class func{
public void getXmlUrl(String fileName){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResource(fileName).toExternalForm();
}
}
