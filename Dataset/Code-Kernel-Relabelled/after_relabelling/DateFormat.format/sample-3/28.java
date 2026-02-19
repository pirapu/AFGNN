public class func{
public void toSafeJSON(){
        if (this.deliveredAt != null) {
            obj.put("deliveredAtText", format.format(deliveredAt.toDate()));
            obj.put("deliveredAtTime", format.format(deliveredAt.getTime()));
        }
}
}
