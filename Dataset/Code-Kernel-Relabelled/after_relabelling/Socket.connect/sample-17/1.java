public class func{
public void connectSocket(Socket sock,InetSocketAddress remoteAddress,InetSocketAddress localAddress,HttpParams params){
        int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
        int soTimeout = HttpConnectionParams.getSoTimeout(params);
            sock.setSoTimeout(soTimeout);
            sock.connect(remoteAddress, connTimeout);
            throw new ConnectTimeoutException("Connect to " + remoteAddress + " timed out");
}
}
