public class func{
public void reportMem(){
  Runtime r = Runtime.getRuntime();
  r.gc();
  return "used: "+((r.totalMemory()-r.freeMemory())/1024L)+"kB (free: "+r.freeMemory()+" of "+r.totalMemory()+")";
}
}
