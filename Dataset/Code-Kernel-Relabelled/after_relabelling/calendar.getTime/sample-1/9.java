public class func{
public void getMinDate(){
      Calendar calendar = new GregorianCalendar();
      calendar.add(Calendar.MONTH, -2);
      minDate = calendar.getTime();
}
}
