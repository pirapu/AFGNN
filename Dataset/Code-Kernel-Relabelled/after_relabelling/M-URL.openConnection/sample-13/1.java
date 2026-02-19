public class func{
public void getImage(URL url){
    URLConnection connection = url.openConnection();
    InputStream stream = connection.getInputStream();
    BufferedInputStream inputbuffer = new BufferedInputStream(stream, 8000);
}
}
