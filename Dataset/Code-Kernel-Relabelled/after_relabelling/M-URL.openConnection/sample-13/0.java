public class func{
public void BinaryIn(URL url){
            URLConnection site = url.openConnection();
            InputStream is     = site.getInputStream();
            in = new BufferedInputStream(is);
}
}
