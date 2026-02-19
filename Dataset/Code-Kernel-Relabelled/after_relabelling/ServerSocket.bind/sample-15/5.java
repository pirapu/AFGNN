public class func{
public void createBoundServerSocket(final InetSocketAddress bindAddress){
        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(bindAddress);
}
}
