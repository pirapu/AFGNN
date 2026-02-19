public class func{
public void addLibrariesToClassPath(Collection<File> libraries){
        for (File library : libraries) {
            try {
                RunHandler.mAddUrl.invoke(RunHandler.classLoader, library.toURI().toURL());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
}
}
