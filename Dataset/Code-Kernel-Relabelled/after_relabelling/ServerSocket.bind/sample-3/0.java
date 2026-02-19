public class func{
public void Acceptor(String host,int port){
            InetSocketAddress address = (host == null) ?
                    new InetSocketAddress(port) :
                    new InetSocketAddress(host, port);
            serverSocket.bind(address);
}
}
