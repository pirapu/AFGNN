public class func{
public void readText(Reader in){
    while ((c = br.read()) >= 0) {
      sb.append((char) c);
    }
    return sb.toString();
}
}
