public class func{
public void runInParallel(ExecutorService exec,Collection<Runnable> runnables){
        for(Runnable runnable : runnables)
            exec.submit(runnable);
        exec.shutdown();
            exec.awaitTermination(1,TimeUnit.DAYS);
}
}
