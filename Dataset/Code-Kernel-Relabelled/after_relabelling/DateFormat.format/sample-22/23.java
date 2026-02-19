public class func{
public void dateTimeToString(Date d,String tzString){
        if (tzString != null)
            df.setTimeZone (TimeZone.getTimeZone (tzString));
        return df.format (d);
}
}
