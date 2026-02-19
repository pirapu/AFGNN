public class func{
public void test_isConnected(){
        serverSocket = new ServerSocket(0, 5);
        theSocket.connect(serverSocket.getLocalSocketAddress());
        servSock = serverSocket.accept();
}
}
