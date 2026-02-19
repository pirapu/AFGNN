public class func{
public static byte[] calculateHMAC_SHA256(byte[] key, byte[] data) {
    try {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKey);  
        mac.init(secretKey);  
        return mac.doFinal(data);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}
