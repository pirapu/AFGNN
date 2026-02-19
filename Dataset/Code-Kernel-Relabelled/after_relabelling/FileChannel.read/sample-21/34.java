public class func{
public void isSameContent(File file,byte[] bytes,int length){
      ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
      while (((bufferLength = fileChannel.read(byteBuffer)) > 0) &&
           (bufferIndex < length)) {
        for (int i = 0; i < bufferLength; i++) {
          if (buffer[i] != bytes[bufferIndex++]) {
            return false;
          }
        }
        byteBuffer.clear();
      }
      if ((bufferIndex != length) || (bufferLength != -1)) {
        return false;
      }
      else {
        return true;
      }
}
}
