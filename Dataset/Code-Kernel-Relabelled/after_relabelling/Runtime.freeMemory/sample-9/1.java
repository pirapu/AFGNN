public class func{
public void getAvaibleRAMMeg(){
    Runtime runtime = Runtime.getRuntime();
    long used = (runtime.totalMemory() - runtime.freeMemory()) / MB;
    return runtime.maxMemory() / MB - used;
}
}
