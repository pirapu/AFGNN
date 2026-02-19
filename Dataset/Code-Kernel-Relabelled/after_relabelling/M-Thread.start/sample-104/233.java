public class func{
public void startMailServer(){
        mailServer = new SimpleSmtpServer(2525);
        Thread t = new Thread(mailServer);
        t.start();
}
}
