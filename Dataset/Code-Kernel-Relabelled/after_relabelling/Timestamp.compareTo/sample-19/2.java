public class func{
public void getMaxTimestamp(FemAbstractColumnSet table,FemRowCountStatistics stats){
        String dmlTime = stats.getDmlTimestamp();
        String analyzeTime = stats.getAnalyzeTimestamp();
        if (analyzeTime == null) {
            if (dmlTime == null) {
                return null;
            } else {
                return Timestamp.valueOf(dmlTime);
            }
        }
        if (dmlTime == null) {
            return Timestamp.valueOf(analyzeTime);
        }
        Timestamp analyzeTimestamp = Timestamp.valueOf(analyzeTime);
        if (dmlTimestamp.compareTo(analyzeTimestamp) > 0) {
            return dmlTimestamp;
        } else {
            return analyzeTimestamp;
        }
}
}
