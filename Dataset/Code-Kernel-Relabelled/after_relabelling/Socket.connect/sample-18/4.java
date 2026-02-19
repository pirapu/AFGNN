public class func{
public void connect(Socket socket,SocketAddress endpoint,int timeout){
    if (socket == null || endpoint == null || timeout < 0) {
      throw new IllegalArgumentException("Illegal argument for connect()");
    }
    SocketChannel ch = socket.getChannel();
    if (ch == null) {
      socket.connect(endpoint, timeout);
    } else {
      SocketIOWithTimeout.connect(ch, endpoint, timeout);
    }
    if (socket.getLocalPort() == socket.getPort() &&
        socket.getLocalAddress().equals(socket.getInetAddress())) {
      LOG.info("Detected a loopback TCP socket, disconnecting it");
      socket.close();
      throw new ConnectException(
        "Localhost targeted connection resulted in a loopback. " +
        "No daemon is listening on the target port.");
    }
}
}
