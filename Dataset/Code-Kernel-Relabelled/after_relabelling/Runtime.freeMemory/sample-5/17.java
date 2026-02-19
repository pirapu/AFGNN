public class func{
public void onActionFromRunGC(){
        Runtime runtime = Runtime.getRuntime();
        long initialFreeMemory = runtime.freeMemory();
        runtime.gc();
        long delta = runtime.freeMemory() - initialFreeMemory;
        alertManager.info(String.format("Garbage collection freed %,.2f Kb of memory.",
                ((double) delta) / 1024.0d));
}
}
