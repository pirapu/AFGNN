public class func{
public void loadGeometryFromEsriShapeDbg(String file_name){
    if (file_name == null) {
      throw new IllegalArgumentException();
    }
      FileInputStream stream = new FileInputStream(file_name);
      FileChannel fchan = stream.getChannel();
      ByteBuffer bb = ByteBuffer.allocate((int) fchan.size());
      fchan.read(bb);
      bb.order(ByteOrder.LITTLE_ENDIAN);
}
}
