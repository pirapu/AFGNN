public class func{
public void printMemoryUsed(){
        Runtime rT = Runtime.getRuntime();
        tM = rT.totalMemory() / 1.0e6;
        fM = rT.freeMemory() / 1.0e6;
}
}
