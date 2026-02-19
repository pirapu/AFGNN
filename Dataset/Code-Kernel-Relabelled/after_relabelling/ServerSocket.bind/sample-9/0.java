public class func{
public void isTcpPortAvailable(InetSocketAddress localAddress){
            ss = new ServerSocket();
            ss.setReuseAddress(false);
            ss.bind(localAddress);
            ss.close();
}
}
