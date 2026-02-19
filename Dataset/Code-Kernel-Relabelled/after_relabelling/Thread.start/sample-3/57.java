public class func{
public void onClick(View arg0){
      Thread thread = new storeCacheThread(storeCacheHandler);
      thread.start();
}
}
