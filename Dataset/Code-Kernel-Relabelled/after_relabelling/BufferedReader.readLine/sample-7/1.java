public class func{
public void readSampleFile(String url){
    BufferedReader br = new BufferedReader(
        new InputStreamReader(getClass().getResourceAsStream(url)));
    while ((line = br.readLine()) != null) {
      fileString += line += "\n";
    }
    br.close();
}
}
