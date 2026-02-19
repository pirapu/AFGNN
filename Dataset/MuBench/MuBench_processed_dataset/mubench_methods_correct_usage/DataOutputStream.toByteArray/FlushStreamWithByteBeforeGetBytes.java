byte[] pattern(byte b) throws IOException {
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  DataOutputStream dos = new DataOutputStream(baos);
  dos.write(b);
  dos.flush();
  return baos.toByteArray();
}
