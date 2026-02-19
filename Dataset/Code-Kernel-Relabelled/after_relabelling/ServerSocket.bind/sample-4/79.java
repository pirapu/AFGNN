public class func{
public void getServerSocket(InetAddress localEp){
        InetSocketAddress address = new InetSocketAddress(localEp, DatabaseDescriptor.getStoragePort());
            socket.bind(address);
}
}
