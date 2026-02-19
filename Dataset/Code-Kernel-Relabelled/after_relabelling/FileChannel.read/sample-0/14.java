public class func{
public void readFromFileChannel(final FileChannel fileChannel,final Buffer buffer){
        if (!buffer.isComposite()) {
            final ByteBuffer bb = buffer.toByteBuffer();
            final int oldPos = bb.position();
            bytesRead = fileChannel.read(bb);
            bb.position(oldPos);
        } else {
            final ByteBufferArray array = buffer.toByteBufferArray();
            bytesRead = fileChannel.read(
                    array.getArray(), 0, array.size());
            array.restore();
            array.recycle();
        }
        if (bytesRead > 0) {
            buffer.position(buffer.position() + (int) bytesRead);
        }
}
}
