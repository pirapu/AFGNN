public class func{
public void setOutputListener(OutputListener listener){
            PipedOutputStream processOut = new PipedOutputStream();
            PipedInputStream externalIn = new PipedInputStream(processOut);
            myOut = new PrintStream(processOut);
            new Thread(new OutputHooker(invokerName + " stdout", externalIn, listener)).start();
}
}
