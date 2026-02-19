public class func{
public void getController(){
    if(singelton == null)
    {
      singelton = new PropertySetController();
      Thread thread = new Thread(singelton);
      thread.start();
    }
}
}
