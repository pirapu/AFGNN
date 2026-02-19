public class func{
public void run(){
            Socket s = new Socket();
                s.setReuseAddress(true);
                s.setSoTimeout(100);
                s.connect(addr);
}
}
