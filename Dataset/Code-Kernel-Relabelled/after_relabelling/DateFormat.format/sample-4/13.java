public class func{
public void toFreemarkerIsoLocal(Date date){
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    df.setTimeZone(tz);
    return df.format(date);
}
}
