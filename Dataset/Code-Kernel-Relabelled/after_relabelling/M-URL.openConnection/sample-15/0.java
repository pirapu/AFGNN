public class func{
public void findSource(URL location){
    URLConnection connection = location.openConnection();
    if (connection instanceof JarURLConnection) {
      return new File(((JarURLConnection) connection).getJarFile().getName());
    }
    return new File(location.getPath());
}
}
