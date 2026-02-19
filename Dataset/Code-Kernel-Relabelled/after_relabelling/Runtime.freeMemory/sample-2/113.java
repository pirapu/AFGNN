public class func{
public void printMemory(Class<? extends Test> testClass){
                Runtime runtime = Runtime.getRuntime();
                long total = runtime.totalMemory();
                long free = runtime.freeMemory();
                Log.d(TAG, "Free memory   : " + free);
}
}
