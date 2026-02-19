public class func{
public void printMemory(){
    Runtime rt = Runtime.getRuntime();
    long total = rt.totalMemory();
    long free = rt.freeMemory();
    long max = rt.maxMemory();
    System.out.println(String.format("total=%dk, free=%dk, max=%dk, use=%dk", total/1024, free/1024, max/1024, (total-free)/1024));
}
}
