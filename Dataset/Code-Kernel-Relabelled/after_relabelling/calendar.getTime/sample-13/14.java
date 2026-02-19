public class func{
public void calculateBeforeMidnight(Date date){
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.SECOND, -1);
    return cal.getTime();
}
}
