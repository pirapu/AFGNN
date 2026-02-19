public class func{
public void appendQueryParameter(URI uri,OAuth2AccessToken accessToken){
      if (uri.getFragment() != null) {
        sb.append("#");
        sb.append(uri.getFragment());
      }
}
}
