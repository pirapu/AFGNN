public class func{
public void testLongDateTimeParsing(){
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG, m_testLocale);
        String formattedDate = format.format(date);
        Date newDate = format.parse(formattedDate);
        assertEquals(m_testLocale.toString(), date, newDate);
}
}
