public class func{
public void retrieveParts(int contentId,int[] start,int[] end){
          for (int j = firstBlock; j <= lastBlock; j++) {
            long readStartOffset = e.getBlockStartOffset(j);
            int bytesToRead = (int) (e.getBlockEndOffset(j) - readStartOffset);
            ByteBuffer buffer = ByteBuffer.allocate(bytesToRead);
            int bytesRead = fileChannel.read(buffer, readStartOffset);
            if (bytesRead < bytesToRead) {
              throw new RuntimeException("Not enough bytes read, " + bytesRead
                  + " < " + bytesToRead);
            }
            decoded.append(decodeBlock(buffer.array(), 0, bytesRead));
          }
          result[i] = decoded.toString().substring(firstChar, firstChar + b - a);
}
}
