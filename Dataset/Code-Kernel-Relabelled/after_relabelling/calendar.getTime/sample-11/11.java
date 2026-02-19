public class func{
public void decodeBirthDate(String ws){
    Calendar c = new GregorianCalendar (2013, Calendar.JANUARY, 1);
    c.add (Calendar.DAY_OF_YEAR, weeks * 7);
    return c.getTime ();
}
}
