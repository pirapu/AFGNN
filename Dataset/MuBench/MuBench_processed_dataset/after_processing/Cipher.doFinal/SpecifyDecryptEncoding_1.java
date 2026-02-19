public class func{
String pattern(Key key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
  Cipher c = Cipher.getInstance("AES");
  c.init(Cipher.DECRYPT_MODE, key);
  return new String(c.doFinal(data));
}
}
