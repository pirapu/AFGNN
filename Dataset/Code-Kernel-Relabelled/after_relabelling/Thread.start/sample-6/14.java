public class func{
public void forCaches(final Logger logger,final Cache... caches){
    logger.error("DebugStatistics object created - do not use in production code");
    final DebugStatistics stats = new DebugStatistics(logger, caches);
    final Thread statsThread = new Thread(stats);
    statsThread.setDaemon(true);
    statsThread.start();
}
}
