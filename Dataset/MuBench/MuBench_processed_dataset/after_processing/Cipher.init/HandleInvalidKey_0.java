public class func{
void pattern(PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
  Cipher cipher = Cipher.getInstance("RSA");
  try {
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
  } catch (InvalidKeyException e) {
  }
}
}
