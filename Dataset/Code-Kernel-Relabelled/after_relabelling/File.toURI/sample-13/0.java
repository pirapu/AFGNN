public class func{
public void getSource(){
              File f = new File(new String(originatingFileName));
              if (f.exists()) {
                return new EclipseFileObject(null, f.toURI(), JavaFileObject.Kind.SOURCE, null);
              }
}
}
