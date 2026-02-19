public class func{
public void fromSqlTimestampNullFarFuture(Timestamp timestamp){
    if (timestamp.compareTo(EFFECTIVE_MAX_TIMESTAMP) > 0) {
      return null;
    }
    return fromSqlTimestamp(timestamp);
}
}
