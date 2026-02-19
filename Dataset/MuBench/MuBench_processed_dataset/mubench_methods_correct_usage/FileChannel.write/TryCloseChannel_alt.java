public void pattern(ByteBuffer content, Path path) throws IOException {
  try (FileChannel out = FileChannel.open(path, StandardOpenOption.WRITE)) {
    out.write(content);
  }
}

