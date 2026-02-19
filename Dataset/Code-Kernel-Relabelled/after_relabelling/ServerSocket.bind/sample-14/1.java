public class func{
public void keepOneInterface(InetAddress addr,int port){
      if (addr != null) {
        server.bind(new InetSocketAddress(addr, port));
      }
      else {
        server.bind(new InetSocketAddress(port));
      }
      Keeper result = new Keeper(server, port);
}
}
