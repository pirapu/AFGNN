public class example_2 {
    public void openConnection(String url) {
        HttpURLConnection urlConnection = null;
        if (proxy != null)
            urlConnection = (HttpURLConnection) u.openConnection(proxy);
        else
            urlConnection = (HttpURLConnection) u.openConnection();
        urlConnection.setRequestProperty("User-Agent", userAgent);
    }
}
