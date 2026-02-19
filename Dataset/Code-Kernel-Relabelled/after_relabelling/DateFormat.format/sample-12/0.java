public class func{
public void testDateTimeFormat(){
      Calendar calendar = Calendar.getInstance();
      calendar.clear();
      calendar.set(1970, 0, 1, 0, 0, 0);
      String actual = format.format(calendar.getTime());
}
}
