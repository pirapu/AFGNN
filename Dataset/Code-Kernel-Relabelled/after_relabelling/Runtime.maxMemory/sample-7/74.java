public class func{
public void info(){
    final Runtime rt = Runtime.getRuntime();
    final long max = rt.maxMemory();
    final long total = rt.totalMemory();
    final long used = total - rt.freeMemory();
    return TOTAL_MEM_C + Performance.format(max, true) + NL
        + RESERVED_MEM_C + Performance.format(total, true) + NL + MEMUSED_C
        + Performance.format(used, true) + NL + NL + H_USED_MEM;
}
}
