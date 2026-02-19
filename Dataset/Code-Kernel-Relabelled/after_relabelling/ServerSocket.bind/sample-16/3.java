public class func{
public void start(){
        if (sslcontext != null) {
            SSLServerSocketFactory sf = sslcontext.getServerSocketFactory();
            ssock = sf.createServerSocket();
        } else {
            ssock = new ServerSocket();
        }
        ssock.setReuseAddress(true);
        ssock.bind(TEST_SERVER_ADDR);
}
}
