public class func{
public void compare(Hashtable<String,String> a,Hashtable<String,String> b){
          String t2Str = b.get("tgtChangeDate");
          if (t1Str.length() != 0 && t2Str.length() != 0) {
            Timestamp t1 = DateUtils.getTimestampFromUTC(t1Str);
            Timestamp t2 = DateUtils.getTimestampFromUTC(t2Str);
            return t2.compareTo(t1);
          } else if (t1Str.length() == 0 && t2Str.length() != 0) {
            return -1;
          }
}
}
