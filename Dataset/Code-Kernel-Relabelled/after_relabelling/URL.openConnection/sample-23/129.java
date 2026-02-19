public class func{
public void remoteResume(URL url,long position,long lastFetchTime,String userAgent,String username,String password){
        URLConnection con = url.openConnection();
        URLConnectionHolder holder = new URLConnectionHolder(con, null);
        return remoteResume(holder, position, lastFetchTime, userAgent, username, password);
}
}
