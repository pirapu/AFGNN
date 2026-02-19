public class func{
public void get(String JsonUrl){
    URL url = new URL(JsonUrl);
    URLConnection urlConnection = url.openConnection();
    urlConnection.setConnectTimeout(10000);
    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(urlConnection.getInputStream()));
}
}
