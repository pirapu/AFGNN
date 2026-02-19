public class func{
public void getLTDate(Calendar cal){
    Calendar ltCal = (Calendar)cal.clone();
    ltCal.set(Calendar.MILLISECOND, 990);
    return ltCal.getTime();
}
}
