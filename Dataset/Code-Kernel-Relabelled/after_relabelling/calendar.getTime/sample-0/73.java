public class func{
public void getNeverExpire(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.SECOND, 59);
    return cal.getTime();
}
}
