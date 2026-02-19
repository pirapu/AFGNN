public class func{
public void formatDate(DateFormat format,Date date){
    if (date==null) return "???";
    else return format.format(date);
}
}
