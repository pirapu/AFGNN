public class func{
public void getLocalPort(ServerSocket socket,String hostname,int port){
        addr = new InetSocketAddress(hostname, port);
        socket.bind(addr);
        log.info("Receiver Server Socket bound to:" + addr);
}
}
