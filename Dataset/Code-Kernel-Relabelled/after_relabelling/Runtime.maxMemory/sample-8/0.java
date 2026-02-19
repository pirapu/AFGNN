public class func{
public void TabletServerResourceManager(TabletServer tserver,VolumeManager fs){
    Runtime runtime = Runtime.getRuntime();
    if (!usingNativeMap && maxMemory + dCacheSize + iCacheSize + totalQueueSize > runtime.maxMemory()) {
      throw new IllegalArgumentException(String.format(
          "Maximum tablet server map memory %,d block cache sizes %,d and mutation queue size %,d is too large for this JVM configuration %,d", maxMemory,
          dCacheSize + iCacheSize, totalQueueSize, runtime.maxMemory()));
    }
    runtime.gc();
    if (!usingNativeMap && maxMemory > runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory())) {
      log.warn("In-memory map may not fit into local memory space.");
    }
}
}
