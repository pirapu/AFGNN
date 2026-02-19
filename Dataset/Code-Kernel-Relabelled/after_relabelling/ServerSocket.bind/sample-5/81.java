public class func{
public void bind(ServerSocket socket,int portstart,int retries){
                addr = new InetSocketAddress(getBind(), portstart);
                socket.bind(addr);
                setPort(portstart);
                log.info("Receiver Server Socket bound to:"+addr);
}
}
