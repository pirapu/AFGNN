public class func{
public void setContent(File file){
        byte[] array = new byte[(int) newsize];
        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
        while (read < newsize) {
            read += fileChannel.read(byteBuffer);
        }
        fileChannel.close();
}
}
