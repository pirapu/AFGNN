public class func{
public void encrypt(String strDataToEncrypt) {
	try {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey secretKey = keyGen.generateKey();
		final int AES_KEYLENGTH = 128;
		byte[] iv = new byte[AES_KEYLENGTH / 8];
		SecureRandom prng = new SecureRandom();
		prng.nextBytes(iv);
		Cipher aesCipherForEncryption = Cipher.getInstance();
		aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, 
				new IvParameterSpec(iv));
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
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
