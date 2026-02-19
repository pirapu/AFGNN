public class func{
public void descendants(final Optional<NodeReadTrx> pRtx){
      executor.submit(new GetDescendants());
      executor.shutdown();
}
}
