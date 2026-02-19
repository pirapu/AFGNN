public class func{
public void missingFinally(File file) throws IOException {
	Writer writer = new PrintWriter(new FileOutputStream(file));
	writer.write("foo");
}
}
