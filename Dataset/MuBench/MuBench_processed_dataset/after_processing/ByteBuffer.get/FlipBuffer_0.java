public class func{
public void pattern(byte[] content) throws IOException {
ByteBuffer buffer = ByteBuffer.allocate(content.length);
buffer.put(content);
buffer.flip();
	buffer.get();
}
}
