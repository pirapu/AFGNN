public class func{
public void getSizeOfFreeHeapMemory(){
    Runtime r = Runtime.getRuntime();
    long maxMemory = r.maxMemory();
    if (maxMemory == Long.MAX_VALUE) {
      try {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        Class<?> clazz = Class.forName("com.sun.management.OperatingSystemMXBean");
        Method method = clazz.getMethod("getTotalPhysicalMemorySize");
        maxMemory = (Long) method.invoke(operatingSystemMXBean) / 4;
      } catch (Throwable e) {
        throw new RuntimeException("Could not determine the amount of free memory.\n" +
            "Please set the maximum memory for the JVM, e.g. -Xmx512M for 512 megabytes.");
      }
    }
    return maxMemory - r.totalMemory() + r.freeMemory();
}
}
