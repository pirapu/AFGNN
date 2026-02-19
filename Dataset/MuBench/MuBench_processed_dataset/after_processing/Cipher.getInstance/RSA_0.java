public class func{
public void encrypt(String strDataToEncrypt, int keyLength) {
	try {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(keyLength);
		KeyPair keyPair = keyGen.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteEncryptedData = cipher.doFinal(byteDataToEncrypt);
	}
	catch (NoSuchAlgorithmException noSuchAlgo) {
	}
	catch (NoSuchPaddingException noSuchPad) {
	}
	catch (InvalidKeyException invalidKey) {
	}
	catch (BadPaddingException badPadding) {
	}
	catch (IllegalBlockSizeException illegalBlockSize) {
	}
	catch (InvalidAlgorithmParameterException invalidParam) {
	}
}
}
