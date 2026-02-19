public class func{
public void readUsingJava(String input,int chunk){
    while ((line = r.readLine()) != null) {
      lines.add(line);
    }
    r.close();
}
}
