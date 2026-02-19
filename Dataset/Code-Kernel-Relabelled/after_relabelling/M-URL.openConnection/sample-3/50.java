public class func{
public void verifyHelperForPostPut(int id){
        URL url = new URL(getURL() + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        InputStream xml = connection.getInputStream();
}
}
