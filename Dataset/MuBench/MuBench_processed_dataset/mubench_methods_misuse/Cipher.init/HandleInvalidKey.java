void pattern(PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
  Cipher cipher = Cipher.getInstance("RSA");
 
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
 
}