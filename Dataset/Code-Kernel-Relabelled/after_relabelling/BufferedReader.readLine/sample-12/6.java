public class func{
public void expectHeader(String header,BufferedReader br){
    String s = br.readLine();
    if (s == null)
      throw new JarException("unexpected end of file");
    return expectHeader(header, br, s);
}
}
