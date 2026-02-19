public class func{
public void startPump(String mode,ConsoleParser parser,InputStream inputStream){
        ConsoleStreamer pump = new ConsoleStreamer(mode,inputStream);
        pump.setParser(parser);
        Thread thread = new Thread(pump,"ConsoleStreamer/" + mode);
        thread.start();
}
}
