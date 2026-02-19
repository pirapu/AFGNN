public class func{
public void generateReport(long now,boolean reset){
        Runtime rt = Runtime.getRuntime();
        long total = rt.totalMemory(), max = rt.maxMemory();
        long used = (total - rt.freeMemory());
}
}
