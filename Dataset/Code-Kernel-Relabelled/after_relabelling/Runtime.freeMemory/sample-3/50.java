public class func{
public void run(){
                Runtime runtime = Runtime.getRuntime();
                long maxMemory = mb(runtime.maxMemory());
                long allocatedMemory = mb(runtime.totalMemory());
                long freeMemory = mb(runtime.freeMemory());
}
}
