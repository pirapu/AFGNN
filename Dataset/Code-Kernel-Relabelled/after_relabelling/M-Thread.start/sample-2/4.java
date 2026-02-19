public class func{
public void updateUnwatchedCounts(){
        Thread t = new UpdateUnwatchThread(String.valueOf(getShowId()));
        t.start();
}
}
