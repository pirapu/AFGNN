public class func{
public void logGCInfo(AccumuloConfiguration conf){
      if (gcTimeIncreasedCount > 3 && mem < rt.maxMemory() * 0.05) {
        log.warn("Running low on memory");
        gcTimeIncreasedCount = 0;
      }
    if (mem != lastMemorySize) {
      sawChange = true;
    }
    if (mem - lastMemorySize <= 0) {
      sign = "";
    }
    sb.append(String.format(" freemem=%,d(%s%,d) totalmem=%,d", mem, sign, (mem - lastMemorySize), rt.totalMemory()));
}
}
