public class func{
public void getLocalizedDate(String date,TimeZone timeZone){
        DateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        fmt.setTimeZone( timeZone );
        Date parsed = fmt.parse( date );
        fmt = new SimpleDateFormat( "yyyy-MM-dd" );
        return fmt.format( parsed );
}
}
