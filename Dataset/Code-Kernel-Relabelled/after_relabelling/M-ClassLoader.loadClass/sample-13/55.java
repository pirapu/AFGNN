public class func{
public void isType(JvmTypeReference type,Object object,ClassLoader classLoader){
      Class<?> typeGuard = classLoader.loadClass(type.getIdentifier());
      return typeGuard.isInstance(object);
}
}
