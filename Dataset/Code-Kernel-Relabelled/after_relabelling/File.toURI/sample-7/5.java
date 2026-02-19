public class func{
public void getInputSource(File f){
            return new InputSource(f.toURI().toURL().toExternalForm());
            return new InputSource(f.getPath());
}
}
