public class func{
public void testToYearMonthWithDefault(){
        controlCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date controlDate = controlCalendar.getTime();
        String tm = dataHelper.toYearMonth(controlDate);
        this.assertEquals("1970-01", tm);
}
}
