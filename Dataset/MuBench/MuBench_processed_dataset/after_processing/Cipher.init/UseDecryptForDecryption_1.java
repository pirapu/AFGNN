public class func{
void pattern(Cipher cipher, PublicKey pubkey, MessageDigest digest, byte[] data) throws GeneralSecurityException {
  cipher.init(Cipher.ENCRYPT_MODE, pubkey);
  int digestLength = digest.getDigestLength();
  byte[] plaintext = cipher.doFinal(data);
  Util.recoverMessage(digestLength, plaintext);
}
}
