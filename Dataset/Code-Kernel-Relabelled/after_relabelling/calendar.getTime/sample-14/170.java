public class func{
public void createDate(int year,int month,int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.clear(Calendar.MILLISECOND);
        return cal.getTime();
}
}
