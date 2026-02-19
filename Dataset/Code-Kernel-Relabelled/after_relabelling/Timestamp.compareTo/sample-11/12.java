public class func{
public void updatePageCount(FemLocalIndex index,Long pageCount,FarragoRepos repos){
        if ((newestLabelTimestamp == null)
            || noExistingStat
            || (newestLabelTimestamp.compareTo(
                    Timestamp.valueOf(
                        indexStats.getAnalyzeTime())) < 0))
        {
            indexStats.setPageCount(pageCount);
            indexStats.setAnalyzeTime(currTimestamp);
        } else {
            indexStats = repos.newFemIndexStatistics();
            indexStats.setLocalIndex(index);
            indexStats.setPageCount(pageCount);
            indexStats.setAnalyzeTime(currTimestamp);
        }
        index.setAnalyzeTime(currTimestamp);
        index.setPageCount(pageCount);
}
}
