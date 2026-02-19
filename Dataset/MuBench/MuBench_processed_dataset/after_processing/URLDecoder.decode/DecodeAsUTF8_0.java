public class func{
String[] decode(String s) {
  try {
    String decodedString = URLDecoder.decode(s, "UTF-8");
    return decodedString.split("\n");
  } catch (UnsupportedEncodingException e) {
    throw new RuntimeException("This JDK does not support UTF-8 encoding", e);
  }
}
}
}
