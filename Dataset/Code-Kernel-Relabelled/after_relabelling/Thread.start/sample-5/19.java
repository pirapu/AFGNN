public class func{
public void startJob(Runnable target){
        final Thread thread = new Thread(target);
        thread.setDaemon(true);
        thread.start();
}
}
