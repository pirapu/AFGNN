public class func{
public void bind(ServerSocket socket,InetSocketAddress address,int backlog,Configuration conf,String rangeConf){
            InetSocketAddress temp = new InetSocketAddress(address.getAddress(),
                port);
            socket.bind(temp, backlog);
}
}
