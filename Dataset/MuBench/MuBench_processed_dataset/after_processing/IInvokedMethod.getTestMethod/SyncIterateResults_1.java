public class func{
ITestContext pattern(ISuite suite) {
  Map<String, ISuiteResult> results = suite.getResults();
    for (ISuiteResult sr : results.values()) {
      ITestContext context = sr.getTestContext();
      return context;
    }
  return null;
}
}
