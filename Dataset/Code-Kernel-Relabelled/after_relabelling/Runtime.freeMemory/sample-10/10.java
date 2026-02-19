public class func{
public void execute(final SQLSession session,final String command,final String parameters){
        final Runtime rt = Runtime.getRuntime();
        final double totalMemory = rt.totalMemory() / (double) ONE_KILOBYTE;
        final double freeMemory = rt.freeMemory() / (double) ONE_KILOBYTE;
        final double maxMemory = rt.maxMemory() / (double) ONE_KILOBYTE;
        info.put("Max memory [KB]", Formatter.formatNumber(maxMemory, 2));
        info.put("Allocated memory [KB]", Formatter.formatNumber(totalMemory, 2));
        info.put("Free memory [KB]", Formatter.formatNumber(freeMemory, 2));
        info.put("Used memory [KB]", Formatter.formatNumber(memoryUsed, 2));
        info.put("Diff. of used memory (now-before) [KB]", Formatter.formatNumber(diffMemory, 2));
        renderInfo(info);
}
}
