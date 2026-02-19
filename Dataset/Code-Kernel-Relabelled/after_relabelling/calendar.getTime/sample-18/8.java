public class func{
public void formatCal(final Calendar calendar){
        if (calendar.getTimeInMillis() < 0L) {
            calendar.setTimeInMillis(10L);
        }
        return df.format(calendar.getTime());
}
}
