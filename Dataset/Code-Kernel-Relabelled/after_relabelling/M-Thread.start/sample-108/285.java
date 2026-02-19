public class func{
public void onReceive(Context context,Intent intent){
    Event event=EventFactory.getEvent(context, intent);
    new Thread(new EventManagerRunner(context,event)).start();
}
}
