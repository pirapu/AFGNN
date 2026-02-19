public class func{
public void HTTPConnectSocket(String host,int port,String proxyHost,int proxyPort){
    DataInputStream is = new DataInputStream(getInputStream());
    String str = is.readLine();
    if (!str.startsWith("HTTP/1.0 200 ")) {
      if (str.startsWith("HTTP/1.0 "))
  str = str.substring(9);
      throw new IOException("Proxy reports \"" + str + "\"");
    }
    do {
      str = is.readLine();
    } while (str.length() != 0);
}
}
