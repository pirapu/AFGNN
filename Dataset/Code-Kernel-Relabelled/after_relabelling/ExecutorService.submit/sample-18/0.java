public class func{
public void future(final Callable<T> task){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> future = executor.submit( task );
        executor.shutdown();
}
}
