public class func{
public void createServerSocket(int port){
      if (port == 0) {
        sock.bind(null);
        serverPort = sock.getLocalPort();
      } else {
        sock.bind(new InetSocketAddress(port));
      }
      throw new IOException("Could not create ServerSocket on port " + port + "." +
                            ioe);
}
}
