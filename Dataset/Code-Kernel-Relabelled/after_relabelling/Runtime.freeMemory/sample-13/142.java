public class func{
public void runCommand(String arg){
    Runtime run = Runtime.getRuntime();
    Process p = run.exec(arg);
    String strReturn = run(p);
    run.freeMemory();
}
}
