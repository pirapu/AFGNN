public class func{
void mapMayContainNull(Map<String, Object> m) {
	if (m.put("foo", new Object()) != null) {
	} else {
	}
}
}
