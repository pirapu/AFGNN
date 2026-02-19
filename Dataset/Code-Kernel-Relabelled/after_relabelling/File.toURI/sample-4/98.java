public class func{
public void createPath(String strCleanPath){
        File file = new File(strCleanPath);
        URI uri = file.toURI();
        return new Path(uri.getPath());
}
}
