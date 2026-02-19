public class func{
public void loadChartData(List<AdmobStats> statsForApp){
      if (statsForApp.size() > 0) {
        timetext = dateFormat.format(statsForApp.get(0).getDate()) + " - "
            + dateFormat.format(statsForApp.get(statsForApp.size() - 1).getDate());
        updateChartHeadline();
      }
}
}
