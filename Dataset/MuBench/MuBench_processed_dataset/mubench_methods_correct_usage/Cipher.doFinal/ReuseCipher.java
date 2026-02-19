List<byte[]> misuse(BufferedReader dataStream, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, key);
    String line;
    List<byte[]> encryptedData = new java.util.ArrayList<byte[]>();
    while ((line = dataStream.readLine()) != null) {
        encryptedData.add(cipher.doFinal(line.getBytes("utf-8")));
    }
    return encryptedData;
}
