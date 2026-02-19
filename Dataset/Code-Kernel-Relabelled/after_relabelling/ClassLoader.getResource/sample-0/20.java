public class func{
public void loadFromSecondaryLoader(String path){
        for (ClassLoader loader : secondaryResourceLoaders) {
            URL url = loader.getResource(path);
            if (url != null) {
                return url;
            }
        }
}
}
