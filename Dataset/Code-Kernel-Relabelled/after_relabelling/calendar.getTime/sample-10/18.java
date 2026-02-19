public class func{
public void shouldRewrapIfAppicationContextRefreshed(){
    Calendar c = Calendar.getInstance();
    c.add(Calendar.SECOND, 1);
    this.applicationMap.put(LAST_REFRESHED_DATE_ATTRIBUTE, c.getTime());
}
}
