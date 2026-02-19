public class func{
public void openStream(final URL url){
        final URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection.getInputStream();
}
}
