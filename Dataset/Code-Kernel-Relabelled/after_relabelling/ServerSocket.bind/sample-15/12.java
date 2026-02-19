public class func{
public void createSocketListener(final InetSocketAddress listenerAddress){
        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(listenerAddress);
        return new SocketListener(serverSocket, this.handleExecutor, this.handleMessageTaskFactory);
}
}
