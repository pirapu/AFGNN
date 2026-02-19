public class func{
String[] decode(String s) {
    String decodedString = URLDecoder.decode(s);
    return decodedString.split("\n");
}
}
