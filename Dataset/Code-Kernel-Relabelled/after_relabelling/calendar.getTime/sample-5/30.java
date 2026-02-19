public class func{
public void setEndDate(Calendar endDate){
        if (endDate != null) {
            this.setEnd(endDate.getTime());
        } else {
            this.setEnd(null);
        }
}
}
