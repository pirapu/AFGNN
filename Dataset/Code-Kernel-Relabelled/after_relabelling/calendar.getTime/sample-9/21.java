public class func{
public void getUpdatedPublications(String spaceId,int since,int nbReturned){
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, maxAge);
      return getUpdatedPublications(spaceId, calendar.getTime(), nbReturned);
    return getPublications(spaceId, nbReturned);
}
}
