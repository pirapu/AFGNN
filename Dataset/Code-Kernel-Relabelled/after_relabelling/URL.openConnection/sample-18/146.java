public class func{
public void Modified(){
        URL testURL = new URL("http://localhost:" + ss.getLocalPort() +
                              "/index.html");
        URLConnection URLConn = testURL.openConnection();
        if (URLConn instanceof HttpURLConnection) {
            httpConn = (HttpURLConnection)URLConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setIfModifiedSince(9990000000000L);
            int response = httpConn.getResponseCode();
            if (response != 304)
                throw new RuntimeException("setModifiedSince failure.");
        }
}
}
