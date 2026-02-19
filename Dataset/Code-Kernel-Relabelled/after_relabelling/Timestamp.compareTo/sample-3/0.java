public class func{
public void getLabelBounds(FemLabel referenceLabel,FarragoRepos repos){
        for (FemLabel label : labels) {
            if (label.getParentLabel() != null) {
                continue;
            }
            String timestamp = label.getCreationTimestamp();
            if (timestamp == null) {
                continue;
            }
            Timestamp labelTimestamp = Timestamp.valueOf(timestamp);
            int rc = referenceTimestamp.compareTo(labelTimestamp);
            if ((rc > 0)
                && ((lowerBound == null)
                    || (labelTimestamp.compareTo(lowerBound) > 0)))
            {
                lowerBound = labelTimestamp;
            } else if (
                (rc < 0)
                && ((upperBound == null)
                    || (labelTimestamp.compareTo(upperBound) < 0)))
            {
                upperBound = labelTimestamp;
            }
        }
}
}
