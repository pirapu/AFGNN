public class func{
public void loadFromFile(final URI uri){
                final String fragment = uri.getFragment();
                if (fragment != null) {
                    if (json.containsField(fragment)) {
                        return json.getObject(fragment);
                    } else {
                        throw new RuntimeException("Fragment #" + fragment + " not found!");
                    }
                }
            throw new RuntimeException(ioe);
}
}
