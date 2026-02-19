public class func{
public void readValue(byte[] key,int chunk,int valueLocation){
                    ByteBuffer sizeBuffer = ByteBuffer.allocate(headerSize);
                    dataFile.read(sizeBuffer, valueLocation);
                    short numKeyValues = sizeBuffer.getShort(0);
                    int valueSize = sizeBuffer.getInt(ByteUtils.SIZE_OF_SHORT
                                                      + ByteUtils.SIZE_OF_INT);
                    do {
                        if(keySize == -1 && valueSize == -1) {
                            sizeBuffer.clear();
                            dataFile.read(sizeBuffer, valueLocation);
                            keySize = sizeBuffer.getInt(0);
                            valueSize = sizeBuffer.getInt(ByteUtils.SIZE_OF_INT);
                            valueLocation += (2 * ByteUtils.SIZE_OF_INT);
                        }
                        ByteBuffer buffer = ByteBuffer.allocate(keySize + valueSize);
                        dataFile.read(buffer, valueLocation);
                        if(ByteUtils.compare(key, buffer.array(), 0, keySize) == 0) {
                            return ByteUtils.copy(buffer.array(), keySize, keySize + valueSize);
                        }
                        valueLocation += (keySize + valueSize);
                        keySize = valueSize = -1;
                    } while(--numKeyValues > 0);
}
}
