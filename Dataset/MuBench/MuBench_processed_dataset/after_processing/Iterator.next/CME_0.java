public class func{
public void misuse(Collection<Object> c) {
Iterator<Object> i = c.iterator();
c.add(new Object());
if (i.hasNext())
	i.next();
}
}
