public class func{
public void getUrlContent(URL u){
    java.net.URLConnection c = u.openConnection();
    c.setConnectTimeout(2000);
    c.setReadTimeout(2000);
    c.connect();
}
}
