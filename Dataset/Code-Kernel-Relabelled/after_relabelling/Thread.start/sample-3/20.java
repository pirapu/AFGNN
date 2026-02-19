public class func{
public void launchManualOperation(){
    final Desktop desktop = Executions.getCurrent().getDesktop();
    desktop.enableServerPush(true);
    Thread manualOperation = manualOperation(desktop);
    manualOperation.start();
}
}
