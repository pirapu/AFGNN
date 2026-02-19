public class func{
public void startConsole(){
                            InetSocketAddress isa = new InetSocketAddress(host, 0);
                            testSock.bind(isa);
                            if (testSock != null) try { testSock.close(); } catch (IOException ioe) {}
}
}
