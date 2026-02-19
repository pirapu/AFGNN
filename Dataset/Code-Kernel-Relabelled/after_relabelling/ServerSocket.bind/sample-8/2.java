public class func{
public void MultipointServer(final String bindHost,String broadcastHost,final int port,final Tracker tracker,final String name,final boolean debug,final Set<URI> roots,Duration reconnectDelay){
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        final ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(address);
        this.port = serverSocket.getLocalPort();
}
}
