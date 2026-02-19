public class func{
public void run(){
        if((page.getStr("BIND")!=null)&&(page.getStr("BIND").trim().length()>0))
          serverSocket.bind (new InetSocketAddress(InetAddress.getByName(page.getStr("BIND")),port));
        else
          serverSocket.bind (new InetSocketAddress (port));
        Log.sysOut("Started "+name+" on port "+port);
}
}
