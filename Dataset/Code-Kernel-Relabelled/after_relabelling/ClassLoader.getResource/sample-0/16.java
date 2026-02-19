public class func{
public void getResource(String name){
    for (ClassLoader pluginClassloader : pluginClassloaders) {
      URL url = pluginClassloader.getResource(name);
      if (url != null) {
        return url;
      }
    }
}
}
