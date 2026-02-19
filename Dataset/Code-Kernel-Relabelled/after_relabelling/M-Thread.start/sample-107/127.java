public class func{
public void runPokenet(){
    Process p = Runtime.getRuntime().exec("java -Dres.path=client/"
        + " -Djava.library.path=client/lib/native " +
    "-Xmx512m -Xms512m -jar ./client/client.jar");
    StreamReader r1 = new StreamReader(p.getInputStream(), "OUTPUT");
    StreamReader r2 = new StreamReader(p.getErrorStream(), "ERROR");
    new Thread(r1).start();
}
}
