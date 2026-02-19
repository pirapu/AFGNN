public class func{
public void VideoCanvas(ZXingMIDlet zXingMIDlet){
    snapshotThread = new SnapshotThread(zXingMIDlet);
    new Thread(snapshotThread).start();
}
}
