public class func{
public void readFromResource(String name,IncludeBulkData includeBulkData){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        DicomInputStream in = new DicomInputStream(
                new File(cl.getResource(name).toURI()));
            in.setIncludeBulkData(includeBulkData);
            in.setAddBulkDataReferences(includeBulkData == IncludeBulkData.URI);
            return in.readDataset(-1, -1);
            in.close();
}
}
