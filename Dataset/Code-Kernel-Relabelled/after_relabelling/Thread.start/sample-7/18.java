public class func{
public void serviceAdded(ServiceEvent serviceEvent){
      GetServiceInfoRunnable r = new GetServiceInfoRunnable(serviceEvent);
      Thread t = new Thread(r);
      t.setDaemon(true);
      t.start();
}
}
