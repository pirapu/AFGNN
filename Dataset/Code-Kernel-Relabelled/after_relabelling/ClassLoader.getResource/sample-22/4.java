public class func{
public void determineFileName(){
        ClassLoader classLoader = (classloader != null) ? classloader : findClassLoader();
        URL url = classLoader.getResource(DEFAULT_CATALOG_WEB);
        if (url != null) {
            return url.toString();
        }
        url = classLoader.getResource(DEFAULT_CATALOG_EJB);
        return url == null? null: url.toString();
}
}
