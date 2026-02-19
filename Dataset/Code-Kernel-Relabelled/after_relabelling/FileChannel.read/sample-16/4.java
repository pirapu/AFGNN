public class func{
public void testFixedLacing(){
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteBuffer source = ByteBuffer.allocate(fileInputStream.available());
            FileChannel channel = fileInputStream.getChannel();
            channel.position(be.dataOffset);
            channel.read(source);
            source.flip();
}
}
