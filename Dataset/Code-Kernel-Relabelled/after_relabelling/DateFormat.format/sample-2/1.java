public class func{
public void asISODateTimeString(final Date date){
    final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_SECONDS);
    df.setTimeZone(DateHelper.UTC);
    return df.format(date);
}
}
