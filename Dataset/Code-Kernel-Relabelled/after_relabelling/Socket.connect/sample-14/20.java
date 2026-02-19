public class func{
public void operate(int cmd,Interp interp,Thing[] argv){
        Socket sock = new Socket();
        sock.connect(isa);
        HeclChannel retval = new HeclChannel(new DataInputStream(sock.getInputStream()),
               new DataOutputStream(sock.getOutputStream()));
        return ObjectThing.create(retval);
}
}
