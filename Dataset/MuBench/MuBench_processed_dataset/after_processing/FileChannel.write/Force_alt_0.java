public class func{
public class Force {
  public void pattern(ByteBuffer content, Path path) throws IOException {
    FileChannel out = FileChannel.open(path, StandardOpenOption.WRITE);
    out.write(content);
    out.force(false);
  }
}
}
