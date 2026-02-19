public class func{
public void fromSqlDateTimeNullFarFuture(Timestamp timestamp){
    if (timestamp.compareTo(EFFECTIVE_MAX_TIMESTAMP) > 0) {
      return null;
    }
    return fromSqlDateTime(timestamp);
}
}
