public class func{
public void getData(HttpServletRequest req){
    String line = reader.readLine();
    while (line != null) {
      sb.append(line + "\n");
      line = reader.readLine();
    }
    reader.close();
}
}
