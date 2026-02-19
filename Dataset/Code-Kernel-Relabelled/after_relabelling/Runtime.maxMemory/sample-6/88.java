public class func{
public void getInfo(Integer div){
  Runtime myRun = Runtime.getRuntime();    
  buff.append("max:"+ (myRun.maxMemory()/div));
  buff.append(",total:"+ (myRun.totalMemory()/div));
  buff.append(",free:"+ (myRun.freeMemory()/div));
  return buff.toString();
}
}
