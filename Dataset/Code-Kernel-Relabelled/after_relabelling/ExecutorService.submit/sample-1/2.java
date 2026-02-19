public class func{
public void testNewCachedThreadPool(){
        executorService.submit(new NoopRunnable());
        executorService.submit(new NoopRunnable());
}
}
