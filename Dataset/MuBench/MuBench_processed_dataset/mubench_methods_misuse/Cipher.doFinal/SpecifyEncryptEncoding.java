byte[] pattern(Key key, String value) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
  Cipher c = Cipher.getInstance("AES");
  c.init(Cipher.ENCRYPT_MODE, key);
  return c.doFinal(value.getBytes());
}