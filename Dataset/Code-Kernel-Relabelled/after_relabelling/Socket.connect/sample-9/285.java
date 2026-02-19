public class func{
public void HttpResponse(HttpBase http,URL url,CrawlDatum datum){
      socket.setSoTimeout(http.getTimeout());
      String sockHost = http.useProxy(url) ? http.getProxyHost() : host;
      int sockPort = http.useProxy(url) ? http.getProxyPort() : port;
      InetSocketAddress sockAddr = new InetSocketAddress(sockHost, sockPort);
      socket.connect(sockAddr, http.getTimeout());
}
}
