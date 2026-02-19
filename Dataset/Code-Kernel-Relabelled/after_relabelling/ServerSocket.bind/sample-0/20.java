public class func{
public void test_setReuseAddressZ(){
        serverSocket = new ServerSocket();
        serverSocket.bind(anyAddress);
        theAddress = serverSocket.getLocalSocketAddress();
}
}
