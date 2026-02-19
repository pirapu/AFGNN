public class func{
public void createServerSocket(String type,InetSocketAddress inetSocketAddress){
            serverSocketChannel = ServerSocketChannel.open();
            serverSocket = serverSocketChannel.socket();
            serverSocket = new ServerSocket();
        serverSocket.bind(inetSocketAddress);
}
}
