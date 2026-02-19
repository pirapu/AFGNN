byte[] pattern(byte b) throws IOException {
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  DataOutputStream dos = new DataOutputStream(baos);
  dos.write(b);
  
  return baos.toByteArray();
}
