public class func{
public void processFile(String src,String dst){
      while ((ch = in.read()) != -1)
        out.append((char)ch);
      out.append("\n");
      in.close();
}
}
