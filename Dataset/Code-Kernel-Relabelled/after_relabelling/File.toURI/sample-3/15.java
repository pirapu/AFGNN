public class func{
public void toUrls(FileCollection collection){
        for (File file : collection.getFiles())
            urls.add(file.toURI().toURL());
        return urls.toArray(new URL[urls.size()]);
}
}
