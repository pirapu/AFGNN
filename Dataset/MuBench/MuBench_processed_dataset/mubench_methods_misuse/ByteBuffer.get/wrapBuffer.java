public void pattern(byte[] content) throws IOException {
  ByteBuffer buffer = ByteBuffer.wrap(content); // <-- implicitly flips
  buffer.get();
}
