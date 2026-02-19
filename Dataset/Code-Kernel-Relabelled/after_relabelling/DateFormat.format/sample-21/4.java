public class func{
public void createTable(){
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, 1);
    String nextMonth = df.format(cal.getTime());
    metricDAO.createMetricTable(METRIC_TABLE_NAME + "_" + nextMonth);
}
}
