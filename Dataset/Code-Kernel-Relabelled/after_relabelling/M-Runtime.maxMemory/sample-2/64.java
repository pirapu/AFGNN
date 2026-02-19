public class func{
public void actionPerformed(ActionEvent ee){
      Runtime currR = Runtime.getRuntime();
      long freeM = currR.freeMemory();
      long totalM = currR.totalMemory();
      long maxM = currR.maxMemory();
      logMessage("Memory (free/total/max.) in bytes: " + printLong(freeM) + " / " + printLong(totalM) + " / " + printLong(maxM));
      statusMessage("Memory (free/total/max.) in bytes: " + printLong(freeM) + " / " + printLong(totalM) + " / " + printLong(maxM));
}
}
