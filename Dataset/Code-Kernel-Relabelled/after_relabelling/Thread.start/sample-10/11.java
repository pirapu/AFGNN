public class func{
public void executeRunnable(Runnable runnable){
        if (runnable != null) {
            Thread thread = new Thread(runnable);
            thread.start();
        } else {
        }
}
}
