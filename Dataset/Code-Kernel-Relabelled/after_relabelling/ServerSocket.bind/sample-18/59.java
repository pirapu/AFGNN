public class func{
public void paintComponent(BufferedImage image,Rectangle[] rectangles){
      String localHostAddress = Utils.getLocalHostAddress();
      if(localHostAddress == null) {
        localHostAddress = "127.0.0.1";
      }
      serverSocket.bind(new InetSocketAddress(InetAddress.getByName(localHostAddress), 0));
}
}
