public class func{
public void run(){
                    Runtime rt = Runtime.getRuntime();
                    byte[] memBytes = longToByteArray(rt.totalMemory());
                    md.update(memBytes, 0, memBytes.length);
                    memBytes = longToByteArray(rt.freeMemory());
                    md.update(memBytes, 0, memBytes.length);
}
}
