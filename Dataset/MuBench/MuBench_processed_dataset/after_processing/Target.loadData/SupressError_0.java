public class func{
public void misuse(Target target) {
	byte[] data = null;
	try {
		data = target.loadData();
	} catch (Throwable t) {
		data = new byte[0];
	}
}
static class Target {
byte[] loadData() throws ParseException { return null; }
}
}
