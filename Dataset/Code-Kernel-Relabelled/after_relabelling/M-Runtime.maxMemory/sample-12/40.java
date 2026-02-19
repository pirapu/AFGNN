public class func{
public void displayMemoryUsage(){
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        String memoryStatus = usedInMegabytes + "M / " + totalInMegabytes + "M / " + (runtime.maxMemory() / mega) + "M";
        if (usedInMegabytes <= lastUsage - MEMORY_THRESHOLD || usedInMegabytes >= lastUsage + MEMORY_THRESHOLD) {
            String threadName = Thread.currentThread().getName();
            System.out.println("%%% [" + threadName + "] Memory usage: " + memoryStatus);
            lastUsage = usedInMegabytes;
        }
}
}
