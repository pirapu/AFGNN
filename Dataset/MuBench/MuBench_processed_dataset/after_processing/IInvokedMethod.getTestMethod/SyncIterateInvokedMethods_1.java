public class func{
ITestNGMethod pattern(ISuite suite) {
  Collection<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
    for (IInvokedMethod iim : invokedMethods) {
      ITestNGMethod tm = iim.getTestMethod();
      return tm;
    }
  return null;
}
}
