public class func{
public void readFromReader(Reader reader){
      BufferedReader br = new BufferedReader(reader);
      String entity = br.readLine();
      br.close();
}
}
