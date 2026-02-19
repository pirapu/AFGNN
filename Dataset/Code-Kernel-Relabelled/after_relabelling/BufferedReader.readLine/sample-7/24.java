public class func{
public void getRepoContent(String path){
    InputStream resourceAsStream = this.getClass().getResourceAsStream(path);
    BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
    while ((line = reader.readLine()) != null) {
      repositoryXML.append(line);
      repositoryXML.append("\r\n");
    }
    return repositoryXML.toString();
}
}
