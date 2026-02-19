public class func{
ITestNGMethod pattern(ISuite suite) {
  Collection<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
  synchronized(invokedMethods) {
    for (IInvokedMethod iim : invokedMethods) {
      ITestNGMethod tm = iim.getTestMethod();
      return tm;
    }
  }
  return null;
}
}
