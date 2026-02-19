public class func{
public void assertDateTime(final String expectedDateTime,final Date date,final TimeZone timeZone){
    final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MINUTES);
    df.setTimeZone(timeZone);
    Assert.assertEquals(expectedDateTime, df.format(date));
}
}
