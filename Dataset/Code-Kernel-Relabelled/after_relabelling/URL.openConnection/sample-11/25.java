public class func{
public void lastModified(){
        URL url = getURL();
        if (ResourceUtils.isFileURL(url) || ResourceUtils.isJarURL(url)) {
            return super.lastModified();
        } else {
            URLConnection con = url.openConnection();
            customizeConnection(con);
            return con.getLastModified();
        }
}
}
