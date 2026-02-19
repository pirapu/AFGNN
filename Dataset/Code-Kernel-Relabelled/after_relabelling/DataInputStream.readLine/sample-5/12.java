public class func{
public void buyPack(int packId,String payForIt){
                input = new DataInputStream (urlConn.getInputStream ());
                while (null != (str = input.readLine()))
                {
                   returnStr.append(str);
                }
                input.close ();
}
}
