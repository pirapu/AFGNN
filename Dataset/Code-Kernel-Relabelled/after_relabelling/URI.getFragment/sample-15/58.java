public class func{
public void safeRelativize(final URI baseURI,final URI toUri){
            if (!Utility.isNullOrEmpty(toUri.getQuery())) {
                sb.append("?");
                sb.append(toUri.getQuery());
            }
            if (!Utility.isNullOrEmpty(toUri.getFragment())) {
                sb.append("#");
                sb.append(toUri.getRawFragment());
            }
}
}
