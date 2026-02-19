public class func{
public void timeReadCD(String filename,Stat stat){
out:while (pos < fileSizeBytes) {
      file.seek( pos);
      int nelems = file.readInt();
      ByteBuffer bbuff = ByteBuffer.allocateDirect( nelems * 4);
      IntBuffer ibuff = bbuff.asIntBuffer();
      channel.read(bbuff);
      int[] data = new int[nelems];
      ibuff.get(data);
      for (int j=0; j<nelems; j++) {
        if (data[j] != j) {
          System.out.println(" bad at pos "+pos+" file= "+filename);
          break out;
        }
      }
      pos += (nelems + 1) * 4;
      nreads++;
    }
    channel.close();
}
}
