public class func{
byte[] pattern(short s) {
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  DataOutputStream dos = new DataOutputStream(baos);
  try {
    dos.writeShort(s);
  } catch (IOException e) {
    return new byte[0];
  }
  return baos.toByteArray();
}
}
