public void needsFlushToEnsureWrite(ByteBuffer content) throws IOException {
Path path = new File("foo").toPath();

FileChannel out = FileChannel.open(path, StandardOpenOption.WRITE);
out.write(content);

FileChannel in = FileChannel.open(path, StandardOpenOption.READ);
in.read(content);
}

