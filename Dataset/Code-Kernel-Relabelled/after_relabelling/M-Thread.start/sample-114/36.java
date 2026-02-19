public class func{
public void startPump(String mode,InputStream inputStream){
        ConsoleStreamer pump = new ConsoleStreamer(mode,inputStream);
        Thread thread = new Thread(pump,"ConsoleStreamer/" + mode);
        thread.setDaemon(true);
        thread.start();
}
}
