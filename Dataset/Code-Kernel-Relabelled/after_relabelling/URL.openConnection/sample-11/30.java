public class func{
public void contentLength(){
    URL url = getURL();
    if (ResourceUtils.isFileURL(url)) {
      return getFile().length();
    }
    else {
      URLConnection con = url.openConnection();
      customizeConnection(con);
      return con.getContentLength();
    }
}
}
