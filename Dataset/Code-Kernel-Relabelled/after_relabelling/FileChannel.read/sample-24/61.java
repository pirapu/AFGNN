public class func{
public void readFully(FileChannel channel,ByteBuffer dst){
        do {
            int r = channel.read(dst);
            if (r < 0) {
                throw new EOFException();
            }
        } while (dst.remaining() > 0);
}
}
