public class func{
public void getPort(int portStart,int retries){
                addr = new InetSocketAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()),
                                             portStart);
                socket.bind(addr);
                socket.close();
}
}
