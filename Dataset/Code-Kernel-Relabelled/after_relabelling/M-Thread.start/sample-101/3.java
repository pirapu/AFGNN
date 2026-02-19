public class func{
public void testThreadManager(){
        X runnable = new X();
        Thread thread = ThreadManager.createBackgroundThread(runnable);
        thread.start();
        thread.join();
}
}
