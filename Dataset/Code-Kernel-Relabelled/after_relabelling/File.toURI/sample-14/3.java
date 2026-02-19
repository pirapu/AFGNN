public class func{
public void makeUrl(String name){
    File file = this.temporaryFolder.newFolder();
    file = new File(file, "classes");
    file.mkdirs();
    return file.toURI().toURL();
}
}
