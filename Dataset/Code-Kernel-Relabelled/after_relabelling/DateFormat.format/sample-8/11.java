public class func{
public void format(Date date,String pattern,String lang,String timezone){
        df.setTimeZone(TimeZone.getTimeZone(timezone));
        return df.format(date);
}
}
