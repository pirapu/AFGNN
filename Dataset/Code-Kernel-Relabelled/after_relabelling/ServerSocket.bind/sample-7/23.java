public class func{
public void initializeDefaultSocketParameters(final InetSocketAddress bindAddress,final InetAddress connectAddress){
            ss = new ServerSocket();
            ss.bind(bindAddress);
}
}
