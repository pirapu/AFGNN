public class func{
public void encrypt(String strDataToEncrypt) {
	try {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
		keyGenerator.init(128);
		SecretKey key = keyGenerator.generateKey();
		Cipher cipher = Cipher.getInstance("Blowfish/CBC/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = cipher.doFinal(byteDataToEncrypt);
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
