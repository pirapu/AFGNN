public class func{
public void formatTimestamp(Calendar timestamp){
    sdf.setTimeZone(timestamp.getTimeZone());
    return sdf.format(timestamp.getTime());
}
}
