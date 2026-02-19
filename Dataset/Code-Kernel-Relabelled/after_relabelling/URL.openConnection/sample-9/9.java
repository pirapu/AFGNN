public class func{
public void output(URL url,File configDir){
        if ("file".equalsIgnoreCase(url.getProtocol())) {
            File f = DataUtilities.urlToFile(url);
            if (!f.isAbsolute()) {
                f = new File(configDir, f.getPath());
            }
            return new FileOutputStream(f);
        }
        else {
            URLConnection cx = url.openConnection();
            cx.setDoOutput(true);
            return cx.getOutputStream();
        }
}
}
