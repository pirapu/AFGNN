public class func{
public void ID3v1Tag(RandomAccessFile file,String loggingFilename){
        fc = file.getChannel();
        fc.position(file.length() - TAG_LENGTH);
        byteBuffer = ByteBuffer.allocate(TAG_LENGTH);
        fc.read(byteBuffer);
        byteBuffer.flip();
        read(byteBuffer);
}
}
