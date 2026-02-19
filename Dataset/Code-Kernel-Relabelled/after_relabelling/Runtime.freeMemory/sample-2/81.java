public class func{
public void printMemory(String message){
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long free = runtime.freeMemory();
        long total = runtime.totalMemory();
}
}
