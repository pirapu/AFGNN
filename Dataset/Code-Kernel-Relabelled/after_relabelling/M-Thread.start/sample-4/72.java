public class func{
public void setErrorListener(OutputListener listener){
            PipedOutputStream processErr = new PipedOutputStream();
            PipedInputStream externalIn = new PipedInputStream(processErr);
            myErr = new PrintStream(processErr);
            new Thread(new OutputHooker(invokerName + " stderr", externalIn, listener)).start();
}
}
