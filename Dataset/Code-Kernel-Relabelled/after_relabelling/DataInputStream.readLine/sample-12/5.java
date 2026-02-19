public class func{
public void connect(){
    String line = dataInput.readLine();
    if (line == null) {
      throw new IOException();
    }
    parseHeaderLine(line);
    line = dataInput.readLine();
    while (line != null && line.length() > 0) {
      String[] strs = line.split(":");
      if (strs.length >= 2) {
        putHeaderField(strs[0], strs[1]);
      }
      line = dataInput.readLine();
    }
}
}
