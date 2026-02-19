public class func{
public void createPreviousDate(int numberOfDays){
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -numberOfDays);
        Date pastDate = DateUtils.getDateWithoutTimeStamp(calendar.getTime());
}
}
