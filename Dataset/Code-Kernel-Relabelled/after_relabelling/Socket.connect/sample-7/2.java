public class func{
public void newSocket(int timeout){
        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.setSoLinger(false, 0);
        socket.setSoTimeout(timeout);
        socket.setTcpNoDelay(true);
        socket.connect(connectadr);
}
}
