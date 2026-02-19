public class func{
public void HttpConnection(Address config,int connectTimeout){
            socketCandidate = (config.proxy != null && config.proxy.type() != Proxy.Type.HTTP)
                    ? new Socket(config.proxy)
                    : new Socket();
                socketCandidate.connect(
                        new InetSocketAddress(addresses[i], config.socketPort), connectTimeout);
                if (i == addresses.length - 1) {
                    throw e;
                }
}
}
