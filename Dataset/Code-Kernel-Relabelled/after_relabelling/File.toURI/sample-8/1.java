public class func{
public void createOutput(String uri,String suggestedFileName){
            File file = new File(GENERATOR_SCHEMA);
            StreamResult result = new StreamResult(file);
            result.setSystemId(file.toURI().toURL().toString());
}
}
