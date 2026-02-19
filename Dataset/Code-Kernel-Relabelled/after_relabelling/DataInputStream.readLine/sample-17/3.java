public class func{
public void parseHeader(DataInputStream in){
      header.file = decodeWebChars(strs[1]);
      if (header.file != null) {
        header.file = URLDecoder.decode(header.file, ISO_8859_1);
        header.parseGetParams();
      }
    line = in.readLine();
    while (line != null) {
      if (line.isEmpty()) {
        break;
      }
      int index = line.indexOf(':');
      if (index == -1) {
        header.headers.put(line, "");
      } else {
        header.headers.put(line.substring(0, index), line.substring(index + 1).trim());
      }
      line = in.readLine();
    }
}
}
