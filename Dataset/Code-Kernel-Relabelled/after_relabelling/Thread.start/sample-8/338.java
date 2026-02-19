public class func{
public void prepareThreads(int threadCount,Runnable runnable){
            Thread thread = new Thread(runnable);
            thread.setUncaughtExceptionHandler(this);
            thread.start();
            threads.add(thread);
}
}
