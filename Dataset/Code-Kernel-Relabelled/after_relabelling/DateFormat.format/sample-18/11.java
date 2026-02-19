public class func{
public void getFormattedTime(DateFormat formatter){
            cal.set(Calendar.MINUTE, getMinute());
            cal.set(Calendar.MILLISECOND, 0);
            return formatter.format(cal.getTime());
}
}
