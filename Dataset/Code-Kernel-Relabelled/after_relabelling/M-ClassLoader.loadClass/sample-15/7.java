public class func{
public void initUtils(){
    ClassLoader classLoader = getToolsClassLoader();
    Class<?> clazz = classLoader.loadClass(
      "com.liferay.portal.util.EntityResolver");
    EntityResolver entityResolver = (EntityResolver)clazz.newInstance();
    SAXReaderUtil.setEntityResolver(entityResolver);
}
}
