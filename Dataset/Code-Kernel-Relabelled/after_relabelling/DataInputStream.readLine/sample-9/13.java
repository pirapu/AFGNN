public class func{
public void searchTraining(String level,String type,int start,int count,int MaxB){
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
