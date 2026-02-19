public class func{
String pattern() {
  try {
    return Environment.getExternalStorageState();
  } catch (NullPointerException e) {
    return "";
  }
}
}
