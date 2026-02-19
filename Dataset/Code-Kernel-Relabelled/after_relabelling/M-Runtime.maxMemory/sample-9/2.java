public class func{
public void getMemoryFreeStatPercentage(){
        Runtime runtime = Runtime.getRuntime();
        if (runtime.maxMemory() != 0) {
            long usedMem = runtime.totalMemory() - runtime.freeMemory();
            return (1 - (1.0f*usedMem)/runtime.maxMemory())*100;
        } else {
            return -1;
        }
}
}
