public class func{
public void safeRelativize(URI baseUri,URI uriToRelativize){
    if (!isNullOrEmpty(uriToRelativize.getQuery())) {
      stringbuilder.append("?");
      stringbuilder.append(uriToRelativize.getQuery());
    }
    if (!isNullOrEmpty(uriToRelativize.getFragment())) {
      stringbuilder.append("#");
      stringbuilder.append(uriToRelativize.getRawFragment());
    }
}
}
