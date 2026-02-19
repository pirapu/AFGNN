public class func{
public void createSocketToEndpoint(final InetSocketAddress targetAddress,final InetSocketAddress bindAddress){
        final Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.bind(bindAddress);
        socket.connect(targetAddress);
}
}
