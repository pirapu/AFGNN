public class func{
public void getFileSystem(){
      URL url = new File(getFile(), "jrt-fs.jar").toURI().toURL();
      ClassLoader loader = new URLClassLoader(new URL[]{url}, null);
      fs = call(newFileSystem, ROOT_URI, EMPTY_ENV, loader);
      myFileSystem = new SoftReference<Object>(fs);
}
}
