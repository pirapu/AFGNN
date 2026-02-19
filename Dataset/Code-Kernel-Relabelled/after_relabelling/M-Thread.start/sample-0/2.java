public class func{
public void start(){
        Thread t = new Thread(new WorkerThread());
        t.setName("image-loader");
        t.start();
}
}
