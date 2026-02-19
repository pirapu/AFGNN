public class func{
public void getDate(Date date,int format,Locale locale){
        DateFormat df = DateFormat.getDateInstance(format, locale);
        return df.format(date);
}
}
