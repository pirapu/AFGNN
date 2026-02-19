public class func{
byte[] pattern(String callId, byte[] secretKey) {
  SecretKeySpec sks = new SecretKeySpec(secretKey, "HmacSHA1");
  Mac mac = Mac.getInstance("HmacSHA1");
  mac.init(sks);
  return mac.doFinal(callId.getBytes());
}
}
