public class func{
public void format(Date date,TimeZone timeZone){
            DateFormat dateFormat = get();
            dateFormat.setTimeZone( timeZone );
            return dateFormat.format( date );
}
}
