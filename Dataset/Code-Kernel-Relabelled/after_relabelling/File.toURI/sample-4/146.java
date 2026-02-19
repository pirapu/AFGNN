public class func{
public void testLocal(){
    File file = new File("output/rafsource/test1.bin");
    URI uri = file.toURI();
    URIDataFileSink sink = new URIDataFileSink(uri);
    sink.writeFile(m_data);
}
}
