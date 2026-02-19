public class func{
public void assertDateSlightlyBefore(Date expected,Date actual){
        Calendar c = Calendar.getInstance();
        c.setTime(expected);
        c.add(Calendar.SECOND, -2);
        assertTrue(actual.after(c.getTime()));
        assertTrue(actual.before(expected) || actual.equals(expected));
}
}
