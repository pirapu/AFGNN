public class func{
public void getFileAsString(File file){
      BufferedReader fileReader = new BufferedReader(new FileReader(file));
      while ((line = fileReader.readLine()) != null) {
         builder.append(line + "\n");
      }
      this.fileCache = builder.toString();
}
}
