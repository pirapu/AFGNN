public class func{
public void shutdown_waitingOver_abruptShutdown(){
    final List<Integer> pool = new ArrayList<Integer>();
    backgroundExecutor.submit(new SleepingRunnable(1, 1000, pool));
    backgroundExecutor.submit(new SleepingRunnable(2, 1000, pool));
    backgroundExecutor.submit(new SleepingRunnable(3, 1000, pool));
    backgroundExecutor.submit(new SleepingRunnable(4, 1000, pool));
    backgroundExecutor.submit(new SleepingRunnable(5, 5000, pool));
    ExecutorServices.shutdown(backgroundExecutor, 100, TimeUnit.MILLISECONDS);
}
}
