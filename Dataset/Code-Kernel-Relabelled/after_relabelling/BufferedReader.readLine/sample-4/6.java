public class func{
public void loadSiteList(){
    BufferedReader in = new BufferedReader(new FileReader(inPath + "top500.list"));
      String line = in.readLine();
      while (line != null) {
          urls.add(line);
          line = in.readLine();
      }
      in.close();
}
}
