public class func{
public void run(){
                Runtime runtime = Runtime.getRuntime();
                String s = String.format("free:%s%% %sKB total:%sKB max:%sKB ", runtime.freeMemory() * 100f / runtime.totalMemory(), runtime.freeMemory(), runtime.totalMemory() / 1024,
                        runtime.maxMemory() / 1024);
                Log.d("memory", s);
}
}
