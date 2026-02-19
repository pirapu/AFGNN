public class func{
public void startThread(){
        Thread decoderThread = new Thread(this);
        decoderThread.setName(THREAD_NAME);
        decoderThread.start();
}
}
