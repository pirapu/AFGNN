public class func{
public void getResource(String name){
        for ( final ClassLoader classLoader : this.classLoaders ) {
            URL url = classLoader.getResource( name );
            if ( url != null ) {
                return url;
            }
        }
}
}
