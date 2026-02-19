public class func{
public void start(){
      executor.submit( createCoordinator() );
      executor.shutdown();
}
}
