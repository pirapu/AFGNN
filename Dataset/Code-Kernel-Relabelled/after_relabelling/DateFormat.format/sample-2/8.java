public class func{
public void getGMTTime(final Date inDate){
        final DateFormat rfc1123Format = new SimpleDateFormat(RFC1123_PATTERN,
                LOCALE_US);
        rfc1123Format.setTimeZone(GMT_ZONE);
        return rfc1123Format.format(inDate);
}
}
