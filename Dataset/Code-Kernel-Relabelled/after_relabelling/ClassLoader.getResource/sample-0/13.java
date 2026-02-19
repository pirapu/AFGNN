public class func{
public void findResource(String name){
        for (ClassLoader loader : loaders) {
            URL url = loader.getResource(name);
            if (url != null) {
                return url;
            }
        }
}
}
