public class func{
public void getHeapUtilizationStats(){
        final Runtime runtime = Runtime.getRuntime();
        return "Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + "M" +
               "\nFree Memory: " + runtime.freeMemory() / mb  + "M" +
               "\nTotal Memory: " + runtime.totalMemory() / mb  + "M" +
               "\nMax Memory: " + runtime.maxMemory() / mb  + "M";
}
}
