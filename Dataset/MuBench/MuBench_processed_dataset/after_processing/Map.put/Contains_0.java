public class func{
public void pattern(Map<String, Object> m, String key, Object value) {
  if (m.containsKey(key)) {
  } else {
    m.put(key, value);
  }
}
}
