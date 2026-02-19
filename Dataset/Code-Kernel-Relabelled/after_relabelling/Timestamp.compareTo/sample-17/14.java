public class func{
public void updateRowCounts(FemAbstractColumnSet table,List<RowCountStat> rowCounts,FarragoRepos repos){
        Timestamp newestLabelTimestamp = getNewestLabelCreationTimestamp(repos);
        Timestamp maxTimestamp = getMaxTimestamp(table, rowCountStats);
        if ((newestLabelTimestamp == null)
            || (maxTimestamp == null)
            || (newestLabelTimestamp.compareTo(maxTimestamp) < 0))
        {
            setNewRowCounts(table, rowCountStats, rowCounts);
        } else {
            FemRowCountStatistics newRowCountStats =
                repos.newFemRowCountStatistics();
            newRowCountStats.setColumnSet(table);
            newRowCountStats.setDmlTimestamp(rowCountStats.getDmlTimestamp());
            newRowCountStats.setRowCount(rowCountStats.getRowCount());
            newRowCountStats.setDeletedRowCount(
                rowCountStats.getDeletedRowCount());
            newRowCountStats.setAnalyzeTimestamp(
                rowCountStats.getAnalyzeTimestamp());
            newRowCountStats.setAnalyzeRowCount(
                rowCountStats.getAnalyzeRowCount());
            setNewRowCounts(table, newRowCountStats, rowCounts);
        }
}
}
