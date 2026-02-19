public class func{
public void getHttpDate(Calendar calendar,Date time){
        calendar.setTime(time);
        return rfc1123Format.format(calendar.getTime());
}
}
