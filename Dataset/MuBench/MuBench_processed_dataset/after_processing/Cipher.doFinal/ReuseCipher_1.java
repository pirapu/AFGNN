public class func{
List<byte[]> misuse(BufferedReader dataStream, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
    String line;
    List<byte[]> encryptedData = new java.util.ArrayList<byte[]>();
    while ((line = dataStream.readLine()) != null) {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encryptedData.add(cipher.doFinal(line.getBytes()));
    }
    return encryptedData;
}
}
