public class func{
public void connect(Socket socket,SocketAddress endpoint,SocketAddress localAddr,int timeout){
      Class remoteClass = endpoint.getClass();
      Preconditions.checkArgument(localClass.equals(remoteClass),
          "Local address %s must be of same family as remote address %s.",
          localAddr, endpoint);
      socket.bind(localAddr);
      if (ch == null) {
        socket.connect(endpoint, timeout);
      } else {
        SocketIOWithTimeout.connect(ch, endpoint, timeout);
      }
}
}
