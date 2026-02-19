public class func{
public void connect(){
               input = new DataInputStream (urlConn.getInputStream ());
                while (null != ((str = input.readLine())))
                {
                }
                Map<String,List<String>> cm = urlConn.getHeaderFields();
                List<String> cl = cm.get("Set-Cookie");
}
}
