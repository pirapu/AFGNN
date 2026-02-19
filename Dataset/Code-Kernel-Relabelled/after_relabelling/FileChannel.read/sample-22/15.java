public class func{
public void validateMessage(FileChannel channel,long start,long len,ByteBuffer buffer){
        buffer.rewind();
        int read = channel.read(buffer, start);
        if (read < 4) return -1;
        int size = buffer.getInt(0);
        if (size < Message.MinHeaderSize) return -1;
        if (next > len) return -1;
        ByteBuffer messageBuffer = ByteBuffer.allocate(size);
        while (messageBuffer.hasRemaining()) {
            read = channel.read(messageBuffer, curr);
            if (read < 0) throw new IllegalStateException("File size changed during recovery!");
            else curr += read;
        }
        messageBuffer.rewind();
        Message message = new Message(messageBuffer);
        if (!message.isValid()) return -1;
        else return next;
}
}
