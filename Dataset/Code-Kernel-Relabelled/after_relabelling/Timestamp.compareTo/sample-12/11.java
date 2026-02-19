public class func{
public void getHistogramForUpdate(FarragoRepos repos,FemAbstractColumn column,boolean createNewHistogram){
            if ((newestLabelTimestamp != null)
                && (newestLabelTimestamp.compareTo(
                        Timestamp.valueOf(histogram.getAnalyzeTime())) > 0))
            {
                if (createNewHistogram) {
                    histogram = repos.newFemColumnHistogram();
                    histogram.setColumn(column);
                } else {
                    histogram = null;
                }
            }
}
}
