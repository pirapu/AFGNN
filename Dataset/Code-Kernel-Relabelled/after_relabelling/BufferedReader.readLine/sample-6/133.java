public class func{
public void readContent(final BufferedReader reader){
    while ((line = reader.readLine()) != null) {
      content += line;
      content += '\n';
    }
}
}
