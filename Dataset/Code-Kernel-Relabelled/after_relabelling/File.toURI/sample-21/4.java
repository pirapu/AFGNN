public class func{
public void getWadlPath(String uri,String configRoot){
        if (Paths.get(uri).isAbsolute()) {
            return new File(uri).toString();
        } else {
            return new File(configRoot, uri).toURI().toString();
        }
}
}
