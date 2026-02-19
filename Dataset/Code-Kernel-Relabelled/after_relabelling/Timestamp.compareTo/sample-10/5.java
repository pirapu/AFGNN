public class func{
public void getPageCount(FemLocalIndex index,Timestamp labelTimestamp){
            return index.getPageCount();
        List<FemIndexStatistics> indexStatsList = index.getIndexStats();
        if (indexStatsList.isEmpty()) {
            return index.getPageCount();
        } else {
            for (int i = indexStatsList.size() - 1; i >= 0; i--) {
                FemIndexStatistics stats = indexStatsList.get(i);
                Timestamp statTime = Timestamp.valueOf(stats.getAnalyzeTime());
                if (statTime.compareTo(labelTimestamp) < 0) {
                    return stats.getPageCount();
                }
            }
        }
}
}
