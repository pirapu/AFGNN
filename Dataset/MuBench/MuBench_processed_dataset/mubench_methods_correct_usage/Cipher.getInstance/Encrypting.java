public static byte[] encryptWithKey(byte[] content) throws Exception {
	// using a constant key is unsafe
	SecretKeySpec keySpec = new SecretKeySpec("RAS".getBytes("UTF-8"), "AES/CBC/PKCS5Padding");
	Cipher c = Cipher.getInstance("AES");
	c.init(Cipher.ENCRYPT_MODE, keySpec);
	return c.doFinal(content);
}