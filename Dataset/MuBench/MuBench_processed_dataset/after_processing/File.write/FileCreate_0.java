public class func{
public void pattern(Path path, byte[] content) throws IOException {
	Files.write(path, content, StandardOpenOption.CREATE);
}
}
