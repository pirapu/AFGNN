public class func{
public void getDocumentFromUrl(String url){
    HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
        .openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setDoOutput(true);
    urlConnection.setDoInput(true);
    urlConnection.connect();
}
}
