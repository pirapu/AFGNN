public class func{
public void possibleOutOfMemory(int neededMemory){
    Runtime runtime = Runtime.getRuntime();
    long maxMemory = runtime.maxMemory();
    long realFreeMemory = runtime.freeMemory() + (maxMemory - runtime.totalMemory());
}
}
