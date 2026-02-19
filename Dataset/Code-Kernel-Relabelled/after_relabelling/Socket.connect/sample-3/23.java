public class func{
public void runAcceptorTest(String testName,SocketAcceptorHelper<?> acceptor){
            Socket socket = new Socket();
            socket.connect(connectAddress, 125);
            socket.close();
}
}
