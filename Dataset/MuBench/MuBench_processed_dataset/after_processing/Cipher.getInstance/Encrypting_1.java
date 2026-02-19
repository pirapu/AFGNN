public class func{
public static byte[] encryptWithKey(byte[] content) throws Exception {
	SecretKeySpec keySpec = new SecretKeySpec("RAS".getBytes("UTF-8"), "AES/CBC/PKCS5Padding");
	Cipher c = Cipher.getInstance();
	c.init(Cipher.ENCRYPT_MODE, keySpec);
	return c.doFinal(content);
}
}
