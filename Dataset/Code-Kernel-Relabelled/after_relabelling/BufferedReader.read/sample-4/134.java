public class func{
public void readTextFile(File file){
    BufferedReader reader = new BufferedReader(new FileReader(file));
      while ((numRead = reader.read(buf)) != -1) {
        String readData = String.valueOf(buf, 0, numRead);
        fileData.append(readData);
        buf = new char[1024];
      }
      IOUtils.cleanup(null, reader);
}
}
