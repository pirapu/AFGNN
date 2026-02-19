public class func{
public void readFile(File file){
    while((line = br.readLine()) != null)
    {
      sb.append(line + "\n");
    }
    return sb.toString();
}
}
