public class func{
void pattern(String pattern, Date date, TimeZone GMT) {
  SimpleDateFormat formatter = new SimpleDateFormat(pattern);
  formatter.setTimeZone(GMT);
  formatter.format(date);
}
}
