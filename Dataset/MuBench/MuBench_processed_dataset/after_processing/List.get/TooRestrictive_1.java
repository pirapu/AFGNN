public class func{
public void misuse(List<String> l) {
	for (int i = 1; i < l.size(); i++) {
		l.get(i - 1);
	}
}
}
