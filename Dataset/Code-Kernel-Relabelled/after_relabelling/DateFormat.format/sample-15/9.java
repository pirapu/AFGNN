public class func{
public void setRecurrenceDate(final Date recurrenceDate){
    df.setTimeZone(PFUserContext.getTimeZone());
    final String recurrenceDateString = df.format(recurrenceDate);
    setRecurrenceDate(recurrenceDateString);
}
}
