public class func{
public void readFile(String filename){
    BufferedReader in = new BufferedReader(new FileReader(filename));
    String line = in.readLine();
    while (line != null) {
      result.add(line);
      line = in.readLine();
    }
    in.close();
}
}
