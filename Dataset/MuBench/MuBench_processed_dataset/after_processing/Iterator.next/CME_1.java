public class func{
public void misuse(Collection<Object> c) {
Iterator<Object> i = c.iterator();
c.add(new Object());
	i.next();
}
}
