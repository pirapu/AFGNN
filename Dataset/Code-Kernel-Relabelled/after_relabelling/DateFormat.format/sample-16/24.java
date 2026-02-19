public class func{
public void compare(final Calendar expected,final Calendar actual){
        final String msg = "Locale: " + locale + " FirstDayOfWeek: " + actual.getFirstDayOfWeek() + " Expected: "
                + f.format(expected.getTime()) + " Actual: " + f.format(actual.getTime());
        assertEquals(msg, expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
        assertEquals(msg, expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(msg, expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(msg, expected.get(Calendar.HOUR), actual.get(Calendar.HOUR));
        assertEquals(msg, expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
}
}
