public class func{
public void createContent(@NotNull ConsoleView consoleView,@NotNull JstdServerStatusView capturingView){
    JComponent consoleComponent = consoleView.getComponent();
    panel.add(consoleComponent, BorderLayout.CENTER);
    ActionToolbar consoleActionToolbar = createActionToolbar(consoleView);
    consoleActionToolbar.setTargetComponent(consoleComponent);
    panel.add(consoleActionToolbar.getComponent(), BorderLayout.WEST);
}
}
