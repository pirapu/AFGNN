public class func{
public void testSetAndGet(){
    Calendar c = Calendar.getInstance();
    Date date = c.getTime();
    d.set(date);
    assertEquals(date, d.get());
}
}
