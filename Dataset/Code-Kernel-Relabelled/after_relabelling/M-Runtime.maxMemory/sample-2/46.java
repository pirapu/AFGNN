public class func{
public void getCpuMemStats(){
    Runtime rt = Runtime.getRuntime();
    long totalMem = rt.totalMemory();
    long maxMem = rt.maxMemory();
    long freeMem = rt.freeMemory();
    return String.format(msg, totalMem / megs, usedMem / megs, maxMem / megs);
}
}
