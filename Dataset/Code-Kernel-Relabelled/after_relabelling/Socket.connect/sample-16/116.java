public class func{
public void open(final String hostname,final int port,final ProxyData proxyData,final int connectTimeout){
            InetAddress addr = TransportManager.createInetAddress(pd.proxyHost);
            sock.connect(new InetSocketAddress(addr, pd.proxyPort), connectTimeout);
            sock.setSoTimeout(0);
}
}
