public class func{
public void newStartedThread(Runnable runnable){
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();
}
}
