public class func{
public void startHistoryUpdaterRunnable(String title,String url,String originalUrl){
      if ((url != null) &&
          (url.length() > 0)) {
        new Thread(new HistoryUpdater(this, title, url, originalUrl)).start();
      }
}
}
