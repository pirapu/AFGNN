public class func{
public void readStringFromFile(String pathFile){
    BufferedReader reader = new BufferedReader(streamReader);
    while ((numRead = reader.read(buf)) != -1) {
      String readData = String.valueOf(buf, 0, numRead);
      fileData.append(readData);
      buf = new char[1024];
    }
    reader.close();
}
}
