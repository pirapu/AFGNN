public void encrypt(String strDataToEncrypt) {
	try {
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		SecretKey secretKey = keyGen.generateKey();

		Cipher desCipher = Cipher.getInstance("DES/CBC/NoPadding");
		desCipher.init(Cipher.ENCRYPT_MODE,secretKey);

		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);
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
}