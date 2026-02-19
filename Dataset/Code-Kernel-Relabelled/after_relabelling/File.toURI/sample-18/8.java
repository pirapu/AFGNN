public class func{
public void resolveFile(File file){
        if (!file.isFile()) {
            throw new ViewNotFoundException(null, null, file.getAbsolutePath());
        }
            return file.toURI().toURL();
}
}
