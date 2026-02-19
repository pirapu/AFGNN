public class func{
byte[] pattern(byte b) throws IOException {
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  DataOutputStream dos = new DataOutputStream(baos);
  dos.writeByte(b);
  dos.flush();
  return baos.toByteArray();
}
}
