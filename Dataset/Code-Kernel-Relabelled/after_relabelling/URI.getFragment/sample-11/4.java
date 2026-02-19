public class func{
public void decodeHarURI(URI rawURI,Configuration conf){
    String tmpAuth = rawURI.getAuthority();
    if (tmpAuth == null) {
      return FileSystem.getDefaultUri(conf);
    }
    String host = rawURI.getHost();
    if (host == null) {
      throw new IOException("URI: " + rawURI
          + " is an invalid Har URI since host==null."
          + "  Expecting har://<scheme>-<host>/<path>.");
    }
    int i = host.indexOf('-');
    if (i < 0) {
      throw new IOException("URI: " + rawURI
          + " is an invalid Har URI since '-' not found."
          + "  Expecting har://<scheme>-<host>/<path>.");
    }
    final String underLyingScheme = host.substring(0, i);
    final String underLyingHost = i == host.length()? null: host.substring(i);
    int underLyingPort = rawURI.getPort();
    String auth = (underLyingHost == null && underLyingPort == -1)?
                  null:(underLyingHost+":"+underLyingPort);
    if (rawURI.getQuery() != null) {
      throw new IOException("query component in Path not supported  " + rawURI);
    }
      tmp = new URI(underLyingScheme, auth, rawURI.getPath(), 
            rawURI.getQuery(), rawURI.getFragment());
}
}
