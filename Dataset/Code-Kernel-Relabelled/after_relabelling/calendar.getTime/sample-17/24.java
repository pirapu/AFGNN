public class func{
public void getNextWorkingDay(Date day){
        Calendar calendarDay = DateUtils.getCalendar(day);
        do {
            calendarDay.add(Calendar.DATE, 1);
        } while (!isWorkingDay(calendarDay));
        return calendarDay.getTime();
}
}
