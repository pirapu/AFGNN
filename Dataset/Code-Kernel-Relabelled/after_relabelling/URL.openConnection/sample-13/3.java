public class func{
public void assertJar(URL url){
    URLConnection conn = url.openConnection();
    conn.connect();
    InputStream in = conn.getInputStream();
}
}
