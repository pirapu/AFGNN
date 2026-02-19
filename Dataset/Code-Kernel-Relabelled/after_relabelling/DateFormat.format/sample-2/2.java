public class func{
public void asISODateString(final Date date,final java.util.TimeZone timeZone){
    final DateFormat df = new SimpleDateFormat(DateFormats.ISO_DATE);
    df.setTimeZone(timeZone);
    return df.format(date);
}
}
