public class func{
public void getFormattedDate(final Object date,final String pattern,final Locale locale,final TimeZone timeZone){
    if (timeZone != null) {
      format.setTimeZone(timeZone);
    }
    return format.format(date);
}
}
