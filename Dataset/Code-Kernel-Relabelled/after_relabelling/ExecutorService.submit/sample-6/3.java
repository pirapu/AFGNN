public class func {
public void executeTasks(Runnable command,ExecutorService pool) {
            futures.add(pool.submit(command));
        for (Future<?> f : futures) {
            f.get();
        }
}
}
