public class func{
public void isPortOpen(String hostname,int port){
            socket = new Socket();
            socket.setReuseAddress(false);
            socket.setSoLinger(false, 1);
            socket.setSoTimeout(TIMEOUT_IN_MILLISECONDS);
            socket.connect(socketAddress, TIMEOUT_IN_MILLISECONDS);
}
}
