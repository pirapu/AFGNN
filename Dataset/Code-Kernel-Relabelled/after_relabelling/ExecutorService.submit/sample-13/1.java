public class func {
public void runTest(Class<?> clazz,int invocationCount,int threadCount,Boolean expected) {
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(threadCount);
        for (int i = threadCount; i > 0; i--) {
            futures.add(executor.submit(new Worker(clazz, invocationCount)));
        }
        for (final Future<Boolean> future : futures) {
            Assert.assertEquals(expected, future.get());
        }
}
}
