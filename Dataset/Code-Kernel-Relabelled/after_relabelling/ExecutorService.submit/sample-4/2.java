public class func {
public void runTest(final Class<?> clazz,final Method method,final int invocationCount,final int threadCount,final Class<?>[] expected){
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final List<Future<Class<?>[]>> futures = new ArrayList<Future<Class<?>[]>>(threadCount);
        for (int i = threadCount; i > 0; i--) {
            futures.add(executor.submit(new Worker(clazz, method, invocationCount)));
        }
        for (final Future<Class<?>[]> future : futures) {
            Assert.assertArrayEquals(future.get(), expected);
        }
}
}
