public class func{
public void createConnection(){
        URL url = new URL(domainApiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Accept", APPLICATION_DMR_ENCODED);
        connection.setRequestProperty("Content-Type", APPLICATION_DMR_ENCODED);
}
}
