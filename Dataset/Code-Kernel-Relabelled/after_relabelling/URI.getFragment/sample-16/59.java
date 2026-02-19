public class func{
public void fromUri(final URI uri,final HttpMethod method){
        int newPort = uri.getPort();
        if (newPort < 0) {
            try {
                newPort = uri.toURL().getDefaultPort();
            } catch (MalformedURLException e) {
                newPort = ANY_PORT;
            }
        }
        return new MatchInfo(uri.getScheme(), uri.getHost(), newPort, uri.getPath(), uri.getQuery(),
                uri.getFragment(), ANY_REALM, method);
}
}
