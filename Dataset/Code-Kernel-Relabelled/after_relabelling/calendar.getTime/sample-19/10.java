public class func{
public void testSetDate(){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        instance.setDate( date );
        assertEquals( "Date value not set correctly", date, instance.getDate() );
}
}
