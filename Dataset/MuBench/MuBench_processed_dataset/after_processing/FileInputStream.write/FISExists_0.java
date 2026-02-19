public class func{
public void misuse(File file) throws IOException {
	try (FileInputStream fis = new FileInputStream(file)) {
	}
}
}
