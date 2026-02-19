public class func{
public void testNewCachedThreadPoolWithThreadFactory(){
        executorService.submit(new NoopRunnable());
        executorService.submit(new NoopRunnable());
}
}
