public class func{
public void findResource(String name){
      for ( ClassLoader classLoader : individualClassLoaders ) {
        final URL resource = classLoader.getResource( name );
        if ( resource != null ) {
          return resource;
        }
      }
}
}
