public class func{
public void test(InetAddress addr){
                s.bind(new InetSocketAddress(addr, 0));
                s.connect(new InetSocketAddress(addr, port));
                ss.accept().close();
}
}
