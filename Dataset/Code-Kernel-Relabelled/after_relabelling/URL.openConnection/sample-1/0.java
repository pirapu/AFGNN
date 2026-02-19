public class func{
public void getInputStreamReader(URL url){
        java.net.URLConnection cnx1 = url.openConnection();
        cnx1.setUseCaches(false);
        return new InputStreamReader(cnx1.getInputStream(), "UTF-8");
}
}
