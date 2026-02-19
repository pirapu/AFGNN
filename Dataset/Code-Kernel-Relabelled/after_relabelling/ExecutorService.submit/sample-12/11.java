public class func{
public void runAsyncInternal(){
        Crate.Tuple<ExecutorService, Boolean> crate = Concurrent.getManagedSingleThreadedExecutorService();
        jdkExecutor = !crate.getPayload2();
        ExecutorService executor = crate.getPayload1();
        executor.submit(callable);
        executor.shutdown();
}
}
