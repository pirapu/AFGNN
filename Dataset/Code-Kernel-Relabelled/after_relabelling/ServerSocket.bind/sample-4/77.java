public class func{
public void listen(InetAddress localEp){
        InetSocketAddress address = new InetSocketAddress(localEp, DatabaseDescriptor.getStoragePort());
            ss.bind(address);
}
}
