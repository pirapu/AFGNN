public class func{
public void dumpMemoryInfo(String msg){
        Runtime rt = Runtime.getRuntime();
        long free = rt.freeMemory();
        rt.gc();
        long freeMemory = rt.freeMemory() / (1024 * 1024);
        long totalMemory = rt.totalMemory() / (1024 * 1024);
        long usedMemory = rt.totalMemory() - rt.freeMemory();
        if (usedMemory > maxUsed) {
            maxUsed = usedMemory;
        }
}
}
