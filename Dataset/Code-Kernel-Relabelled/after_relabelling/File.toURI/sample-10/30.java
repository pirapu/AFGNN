public class func{
public void doGetResource(String path){
        String realPath = servletContext.getRealPath(path);
        if (realPath != null) {
          return new File(realPath).toURI().toURL();
        }
        else {
          return servletContext.getResource(path);
        }
}
}
