public class func{
public void search(int start,int count,String level,String formation,String position,int nationality,int league,int team,int minBid,int maxBid,int minBIN,int maxBIN){
                urlConn = url.openConnection();
                urlConn.setDoInput (true);
                urlConn.setUseCaches (false);
                urlConn.addRequestProperty("Cookie", EASW_KEY+";"+PhishingKey);
                input = new DataInputStream (urlConn.getInputStream ());
                while (null != (str = input.readLine()))
                {
                   returnStr.append(str);
                }
                input.close ();
}
}
