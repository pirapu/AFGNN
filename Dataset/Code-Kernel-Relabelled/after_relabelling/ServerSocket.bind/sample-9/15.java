public class func{
public void isSocketBindable(InetSocketAddress addr){
    ServerSocket socket = new ServerSocket();
      socket.bind(addr);
      socket.close();
}
}
