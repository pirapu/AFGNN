public class func{
public void makeURIFromFilespec(final String filespec,final String relativePrefix){
    File file = new File(filespec);
    if (relativePrefix != null && !file.isAbsolute()) {
      file = new File(relativePrefix, filespec);
    }
    return file.toURI();
}
}
