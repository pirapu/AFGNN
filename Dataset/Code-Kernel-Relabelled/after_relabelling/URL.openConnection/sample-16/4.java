public class func{
public void getUrlPageHtml(String urlStr,InetSocketAddress addr){
         URLConnection connection = url.openConnection(new Proxy(Proxy.Type.HTTP, addr));
         BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         while ((line = rd.readLine()) != null) {
            buffer.append(line);
         }
         rd.close();
}
}
