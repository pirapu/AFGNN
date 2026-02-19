public class func{
public void getResourceAsStream(String resourceName,ClassLoader callingClass,boolean useCache){
    URL url = getResourceUrl(resourceName, callingClass);
    if (url != null) {
      URLConnection urlConnection = url.openConnection();
      urlConnection.setUseCaches(useCache);
      return urlConnection.getInputStream();
    }
}
}
