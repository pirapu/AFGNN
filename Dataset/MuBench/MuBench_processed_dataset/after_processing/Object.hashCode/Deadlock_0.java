public class func{
public void misuse(Object o) {
	synchronized (o) {
		o.hashCode();
	}
}
}
