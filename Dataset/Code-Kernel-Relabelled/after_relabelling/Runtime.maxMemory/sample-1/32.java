public class func{
public void testMemoryLimit(){
    Runtime runtime = Runtime.getRuntime(); 
    return (runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory())) < MIN_FREE_MEMORY_BYTES;
}
}
