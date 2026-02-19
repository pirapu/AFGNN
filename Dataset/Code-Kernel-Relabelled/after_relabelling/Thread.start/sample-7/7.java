public class func{
public void createWorker(String nodeId){
         Worker worker = new Worker(this, port, nodeId);
         workers.add(worker);
         Thread t = new Thread(worker);
         t.setDaemon(true);
         t.start();
}
}
