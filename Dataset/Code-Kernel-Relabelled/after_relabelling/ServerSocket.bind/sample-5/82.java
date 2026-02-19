public class func{
public void bind(ServerSocket socket,int portstart,int retries){
                    addr = new InetSocketAddress(getBind(), port);
                    socket.bind(addr);
                    setPort(port);
                    log.info("Receiver Server Socket bound to:"+addr);
}
}
