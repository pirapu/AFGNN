public class func{
public void getHistogram(FemAbstractColumn column,Timestamp labelTimestamp){
        List<FemColumnHistogram> histogramList = column.getHistogram();
        int listSize = histogramList.size();
        if ((labelTimestamp == null) && (listSize > 0)) {
            return histogramList.get(listSize - 1);
        }
        for (int i = listSize - 1; i >= 0; i--) {
            FemColumnHistogram histogram = histogramList.get(i);
            Timestamp statTime = Timestamp.valueOf(histogram.getAnalyzeTime());
            if (statTime.compareTo(labelTimestamp) < 0) {
                return histogram;
            }
        }
}
}
