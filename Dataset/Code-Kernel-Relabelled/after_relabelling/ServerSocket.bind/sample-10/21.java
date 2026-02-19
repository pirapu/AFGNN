public class func{
public void start(Properties props){
        String v = props.getProperty(ADDRESS_PROPERTY);
        InetAddress address = v == null ? null : InetAddress.getByName(v);
        int port = Integer.parseInt(props.getProperty(PORT_PROPERTY, "0"));
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        socket.bind(socketAddress);
        Main.log("Started server listening on " + socket.getLocalSocketAddress() + ":"
            + socket.getLocalPort());
}
}
