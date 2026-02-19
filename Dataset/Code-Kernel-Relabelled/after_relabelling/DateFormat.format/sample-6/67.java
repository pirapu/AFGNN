public class func{
public void compareDates(final String format,final Date date1,final Date date2){
        final DateFormat dateFormat = new SimpleDateFormat(format);
        final String date2String = dateFormat.format(date2);
        return date1String.compareTo(date2String);
}
}
