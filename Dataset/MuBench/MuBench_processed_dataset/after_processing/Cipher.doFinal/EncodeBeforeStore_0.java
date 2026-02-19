public class func{
String pattern(Key key, String value) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
  Cipher c = Cipher.getInstance("AES");
  c.init(Cipher.ENCRYPT_MODE, key);
  byte[] result = c.doFinal(value.getBytes("UTF8"));
  return new String(Base64.encode(result));
}
}
