public class func{
public void needsFlipToCopyWhatWasWritten(byte[] result) throws IOException {
ByteBuffer buffer = ByteBuffer.allocate(result.length);
buffer.put(result);
Path fp = FileSystems.getDefault().getPath("output.file");
FileChannel outChannel = FileChannel
		.open(fp, EnumSet.of(StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE));
outChannel.write(buffer);
outChannel.force(false);
}
}
