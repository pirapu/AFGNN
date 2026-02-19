public static byte[] calculateHMAC_SHA256(byte[] key, byte[] data) {
    try {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");

        // Corrected: Initialize the Mac instance only once
        mac.init(secretKey);

        return mac.doFinal(data);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

