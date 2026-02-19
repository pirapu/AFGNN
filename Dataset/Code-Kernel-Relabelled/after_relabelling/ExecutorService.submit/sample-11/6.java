public class func{
public void internalNonBlockingStart(){
    ExecutorService executors = Executors.newFixedThreadPool( 1 );
    future = executors.submit( stackRunner );
    executors.shutdown();
}
}
