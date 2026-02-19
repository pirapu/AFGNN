public class func{
public void getIcon(String iconName){
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource("test/check/icons/" + iconName + ".gif");
    if (url != null)
      return new ImageIcon(url);
    url = cl.getResource("test/check/icons/" + iconName + ".png");
    if (url != null)
      return new ImageIcon(url);
}
}
