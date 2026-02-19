public class func{
public void readFully(FileChannel file,long pos,ByteBuffer dst){
            do {
                int len = file.read(dst, pos);
                if (len < 0) {
                    throw new EOFException();
                }
                pos += len;
            } while (dst.remaining() > 0);
            dst.rewind();
}
}
