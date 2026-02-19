public class func{
public void readValue(byte[] key,int chunk,int valueLocation){
                    ByteBuffer sizeBuffer = ByteBuffer.allocate(ByteUtils.SIZE_OF_INT);
                    dataFile.read(sizeBuffer, valueLocation);
                    int valueSize = sizeBuffer.getInt(0);
                    ByteBuffer valueBuffer = ByteBuffer.allocate(valueSize);
                    dataFile.read(valueBuffer, valueLocation + ByteUtils.SIZE_OF_INT);
                    return valueBuffer.array();
}
}
