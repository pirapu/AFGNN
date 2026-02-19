public class func{
public void load(ByteBasedList l,File f){
      ByteBuffer buf0 = ByteBuffer.allocate(hdr);
      while (buf0.hasRemaining())
        fc.read(buf0);
      buf0.flip();
      l.currentSize = buf0.getInt();
      l.lastModified = buf0.getLong();
      ByteBuffer bytesB = ByteBuffer.allocate(l.bytesUsed);
      while (fc.read(bufs) > 0);
      offsetB.flip();
      IntBuffer iB = offsetB.asIntBuffer();
      iB.get(l.offsetsArray);
}
}
