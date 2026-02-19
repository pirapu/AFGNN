public class func{
public void compress(String sourceDir){
                    while (!eof && buff.remaining() < 512 * 1024) {
                        int remaining = buff.remaining();
                        buff.compact();
                        buff.position(remaining);
                        int l = fc.read(buff);
                        if (l < 0) {
                            eof = true;
                        }
                        buff.flip();
                    }
                    if (buff.remaining() == 0) {
                        break;
                    }
                    int c = getChunkLength(buff.array(), buff.position(),
                            buff.limit()) - buff.position();
                    System.arraycopy(buff.array(), buff.position(), bytes, 0, c);
                    buff.position(buff.position() + c);
                    int[] key = getKey(bucket, bytes);
}
}
