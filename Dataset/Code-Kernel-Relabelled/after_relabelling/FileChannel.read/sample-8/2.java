public class func{
public void read(long start,ByteBuffer dst){
        ch.position(start - entry.getKey());
        return ch.read(dst);
}
}
