public class func{
public void getYear(){
        Calendar cal = Calendar.getInstance();
        Log.d(TAG, dateFormat.format(cal.getTime()));
        return dateFormat.format(cal.getTime());
}
}
