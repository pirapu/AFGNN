public class func{
public void before(){
        File parentDir = new File("parent");
        parentDir.mkdir();
        FileUtils.writeStringToFile(new File(parentDir,"touch"),"hello");
        conf.set(ConfigurableSentenceIterator.ROOT_PATH,parentDir.toURI().toString());
}
}
