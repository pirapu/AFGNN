public class func{
public void openConnection(String query){
    final URL url = new URL(query.replace(" ", "%20"));
    URLConnection urlconnec = url.openConnection();
    urlconnec.setConnectTimeout(15000);
    urlconnec.setReadTimeout(15000);
    return urlconnec.getInputStream();
}
}
