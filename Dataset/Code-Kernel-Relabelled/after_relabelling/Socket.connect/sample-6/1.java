public class func{
public void apply(HostAndPort socketA){
      InetSocketAddress socketAddress = new InetSocketAddress(socketA.getHostText(), socketA
               .getPort());
         logger.trace("testing socket %s", socketAddress);
         socket = new Socket(
               proxyForURI.apply(URI.create("socket://" + socketA.getHostText() + ":" + socketA.getPort())));
         socket.setReuseAddress(false);
         socket.setSoLinger(false, 1);
         socket.setSoTimeout(timeout);
         socket.connect(socketAddress, timeout);
}
}
