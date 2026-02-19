public class func{
public void read(ByteBuffer dst){
        FileChannel channel = getFileChannel();
        channel.position(offset);
        len = channel.read(dst);
}
}
