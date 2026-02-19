public class func{
public void initializeMemory(){
    Runtime rt = Runtime.getRuntime();
    setMaximumHeapSize(rt.maxMemory());
    setFreeHeapSize(rt.freeMemory());
}
}
