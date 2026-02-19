public class func{
public void createKeySigPanel(PGPPublicKey key){
        Iterator<?> iter = key.getSignatures();
        while (iter.hasNext()) {
            PGPSignature sig = (PGPSignature)iter.next();
            String uid = "0x"+Long.toHexString(sig.getKeyID()).substring(8).toUpperCase();
            p.add(new JLabel(uid));
        }
}
}
