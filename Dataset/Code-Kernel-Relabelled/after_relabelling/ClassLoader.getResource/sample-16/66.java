public class func{
public void getResource(BundleWiring bundleWiring,String name){
    Bundle bundle = bundleWiring.getBundle();
    URL url = bundle.getResource(name);
    if ((url == null) && (bundle.getBundleId() == 0)) {
      ClassLoader classLoader = bundleWiring.getClassLoader();
      return classLoader.getResource(name);
    }
    return bundle.getResource(name);
}
}
