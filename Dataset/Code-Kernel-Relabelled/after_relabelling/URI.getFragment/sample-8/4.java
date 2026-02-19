public class func{
public void showURI(final String message,final URI uri){
        System.err.println("0.0.0 string:      "+uri.toString());
        System.err.println("0.0.0 ascii :      "+uri.toASCIIString());
        System.err.println("1.0.0 scheme:      "+uri.getScheme());
        System.err.println("2.0.0 scheme-part: "+uri.getRawSchemeSpecificPart()+" (raw), "+uri.getSchemeSpecificPart()+" (dec)");
        System.err.println("2.1.0 auth:        "+uri.getRawAuthority()+" (raw), "+uri.getAuthority()+" (dec)");
        System.err.println("2.1.1 user-info:   "+uri.getRawUserInfo()+" (raw), "+uri.getUserInfo()+" (dec)");
        System.err.println("2.1.1 host:        "+uri.getHost());
        System.err.println("2.1.1 port:        "+uri.getPort());
        System.err.println("2.2.0 path:        "+uri.getRawPath()+" (raw), "+uri.getPath()+" (dec)");
        System.err.println("2.3.0 query:       "+uri.getRawQuery()+" (raw), "+uri.getQuery()+" (dec)");
        System.err.println("3.0.0 fragment:    "+uri.getRawFragment()+" (raw), "+uri.getFragment()+" (dec)");
}
}
