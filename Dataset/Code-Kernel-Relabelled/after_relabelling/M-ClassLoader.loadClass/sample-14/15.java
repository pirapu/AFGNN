public class func{
public void exec(File jarFile,String[] args,ClassLoader classLoader){
    String mainClassName = getMainClassName(jarFile);
    if (mainClassName == null) {
      throw new ClassNotFoundException("Unable to extract name of Main-Class of " + jarFile.getAbsolutePath() );
    }
    Class mainClass = classLoader.loadClass(mainClassName);
    Method mainMethod = mainClass.getMethod("main", new Class[]{ String[].class } );
    mainMethod.invoke(null, new Object[]{ args } );
}
}
