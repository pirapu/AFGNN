public class func{
public void readHeader(DataInputStream in){
    String buf = in.readLine();
    if (buf == null) {
      throw new IOException("Unexpected EOF reading magic token");
    }
    if (buf.charAt(0) == '#' && buf.charAt(1) == '?') {
      valid |= VALID_PROGRAMTYPE;
      programType = buf.substring(2);
      buf = in.readLine();
      if (buf == null) {
        throw new IOException("Unexpected EOF reading line after magic token");
      }
    }
}
}
