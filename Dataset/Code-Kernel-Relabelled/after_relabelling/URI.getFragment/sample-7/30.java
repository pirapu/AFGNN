public class func{
public void toHTTP(URI uri){
    if (uri == null)
      return uri;
    String scheme = uri.getScheme();
    if (HTTP_SCHEME.equals(scheme) || HTTPS_SCHEME.equals(scheme))
      return uri;
    if (SyncUtils.READER_HTTPS_SCHEME.equals(scheme))
      newScheme = HTTPS_SCHEME;
      return new URI(newScheme, uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
}
}
