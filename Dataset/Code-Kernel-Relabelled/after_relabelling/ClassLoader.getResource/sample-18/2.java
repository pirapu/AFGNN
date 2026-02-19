public class func{
public void getURL(String fileName){
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    url = cl.getResource(fileName);
    if ( url==null ) {
      cl = this.getClass().getClassLoader();
      url = cl.getResource(fileName);
    }
}
}
