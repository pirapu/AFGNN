public class func{
public void addSchemeHostPort(URI uri){
    String scheme = uri.getScheme();
    String host = uri.getHost();
    int port = uri.getPort();
    if (scheme == null) {
      scheme = "http";
    }
    if (host == null) {
      host = "localhost";
    }
    if (port == -1) {
      port = 8080;
    }
      return new URI(scheme, uri.getUserInfo(), host, port, uri.getPath(), uri.getQuery(), uri.getFragment());
      throw new RuntimeException(e);
}
}
