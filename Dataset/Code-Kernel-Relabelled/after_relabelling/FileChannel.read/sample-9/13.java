public class func{
public void readFileAsByteBuffer(String inputFile,boolean directMemory){
      bb = ByteBuffer.allocateDirect((int) l);
      bb = ByteBuffer.allocate((int) l);
    int read = fc.read(bb);
    fc.close();
}
}
