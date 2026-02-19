String pattern(Key key, String value) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
  Cipher c = Cipher.getInstance("AES");
  c.init(Cipher.DECRYPT_MODE, key);
  return new String(c.doFinal(Base64.decode(value)));
}