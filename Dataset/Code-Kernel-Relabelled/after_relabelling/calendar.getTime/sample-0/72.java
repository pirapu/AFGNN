public class func{
public void date(int year,int month,int day,int hour,int minute){
      Calendar c = Calendar.getInstance();
      c.set(Calendar.MILLISECOND, 0);
      return c.getTime();
}
}
