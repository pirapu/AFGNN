public class func{
void pattern(byte[] messageData, byte[] messageSubject, byte[] encryptionKey) throws Exception {
  SecretKeySpec sks = new SecretKeySpec(encryptionKey, "Blowfish");      
  Cipher c = Cipher.getInstance("Blowfish");
  c.init(Cipher.ENCRYPT_MODE, sks);
  byte[] decryptedMsgData = c.doFinal(messageData);
  byte[] decryptedSubject = c.doFinal(messageSubject);
  byte[] encodedSubject = Base64.encodeBase64Chunked(decryptedSubject);
  byte[] encodedBody = Base64.encodeBase64Chunked(decryptedMsgData);
}
}
