public class func{
public void firstLine(String fileName){
    BufferedReader in = new BufferedReader(new FileReader(fileName));
    String rval = in.readLine();
    in.close();
}
}
