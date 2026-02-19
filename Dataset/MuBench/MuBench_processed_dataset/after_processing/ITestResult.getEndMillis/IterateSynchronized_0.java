public class func{
void pattern() {
    synchronized(syncL) {
      for (ITestResult tr : syncL) {
        long elapsedTimeMillis= tr.getEndMillis() - tr.getStartMillis();
        String name= tr.getMethod().isTest() ? tr.getName() : Utils.detailedMethodName(tr.getMethod(), false);
      }
    }
  }
}
