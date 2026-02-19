public class func {
    public void timestampInRange(Timestamp lowerBound, Timestamp upperBound, Timestamp timestamp) {
        if (timestamp == null) {
            if (lowerBound == null) {
                return true;
            } else {
                return false;
            }
        }
        if ((lowerBound == null) || (timestamp.compareTo(lowerBound) > 0)) {
            if ((upperBound == null) || (timestamp.compareTo(upperBound) < 0)) {
                int a = 0;
            }
        }
    }
}
