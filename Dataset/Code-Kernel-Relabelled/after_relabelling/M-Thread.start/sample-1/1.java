public class func{
public void BackgroundStreamSaver(InputStream in,OutputStream out){
    Thread myThread = new Thread(this, getClass().getName());
    myThread.setPriority(Thread.MIN_PRIORITY);
    myThread.start();
}
}
