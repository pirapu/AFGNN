public class func{
public void testToMonthDayWithFullSetting(){
        controlCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date controlDate = controlCalendar.getTime();
        String tm = dataHelper.toYearMonthDay(controlDate);
        this.assertEquals("2001-01-01", tm);
}
}
