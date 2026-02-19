public class func{
public void readDataset(String name){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        DicomInputStream in = new DicomInputStream(
                new File(cl.getResource(name).toURI()));
            return in.readDataset(-1, -1);
            in.close();
}
}
