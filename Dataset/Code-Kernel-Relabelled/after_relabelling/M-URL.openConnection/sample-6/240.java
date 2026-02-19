public class func{
public void connectionTo(final String location){
        final URL url = new URL(location);
        final URLConnection urlConnection = url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(false);
        urlConnection.setAllowUserInteraction(false);
        addBasicAuth(url, urlConnection);
}
}
