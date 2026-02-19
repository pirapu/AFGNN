public class func{
public void createSocket(final String host,final int port,final InetAddress localAddress,final int localPort,final HttpConnectionParams params){
            socket = sslfac.createSocket(host, port, localAddress, localPort);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
        setSocket(socket);
        return wrapSocket(socket);
}
}
