public class func{
public void createServerSocketUsingPortRange(InetAddress ba,int backlog,boolean isBindAddress,boolean useNIO,int tcpBufferSize,int[] tcpPortRange){
          InetSocketAddress addr = new InetSocketAddress(isBindAddress ? ba : null, localPort);
          socket.bind(addr, backlog);
}
}
