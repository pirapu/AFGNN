public class func {
public void runConcurrently(final Callable<Void>... tasks){
    for (final Callable<Void> task : tasks) {
      futures.add(service.submit(task));
    }
    for (final Future<?> future : futures) {
      future.get();
    }
}
}
