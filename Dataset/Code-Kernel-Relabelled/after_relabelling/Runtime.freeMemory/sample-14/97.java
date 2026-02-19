public class func{
public void getStats(){
        Runtime runtime = Runtime.getRuntime();
        stats.put("jvm_free_memory", String.valueOf(runtime.freeMemory()));
        stats.put("jvm_max_memory", String.valueOf(runtime.maxMemory()));
        stats.put("jvm_total_memory", String.valueOf(runtime.totalMemory()));
}
}
