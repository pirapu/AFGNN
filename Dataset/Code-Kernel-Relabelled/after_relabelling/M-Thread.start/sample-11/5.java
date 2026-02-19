public class func{
public void startConsoleConsumer(final InputStream stream,final String shutdownId){
        final ConsoleConsumer result = new ConsoleConsumer(stream, shutdownId);
        final Thread t = new Thread(result);
        t.setName("AS7-Console");
        t.start();
}
}
