public class func{
String pattern(JsonElement d) {
  if (!d.isJsonNull())
    return d.getAsString();
  else
    return "";
}
}
