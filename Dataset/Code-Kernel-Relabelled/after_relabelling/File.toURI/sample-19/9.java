public class func{
public void getResource(final String name){
    final File f = new File(this.root, name);
    if (f.canRead()) {
      return f.toURI().toURL();
    } else {
      return null;
    }
}
}
