public class func{
public void getSourceLocation(Class<?> clazz){
    final ClassLoader loader = clazz.getClassLoader();
    final URL resource = loader != null ? loader.getResource(name) : ClassLoader.getSystemResource(name);
    return resource != null ? resource.toExternalForm() : "<unknown>";
}
}
