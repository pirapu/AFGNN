public class func{
public void pickAddress(){
                    if (bindAny) {
                        inetSocketAddress = new InetSocketAddress(port);
                    } else {
                        inetSocketAddress = new InetSocketAddress(bindAddressDef.inetAddress, port);
                    }
                    log(Level.FINEST, "Trying to bind inet socket address:" + inetSocketAddress);
                    serverSocket.bind(inetSocketAddress, 100);
                    log(Level.FINEST, "Bind successful to inet socket address:" + inetSocketAddress);
}
}
