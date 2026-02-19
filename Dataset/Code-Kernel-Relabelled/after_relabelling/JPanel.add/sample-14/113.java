public class func{
public void installActionGroupInToolBar(DefaultActionGroup actionGroup,JPanel toolBarPanel,ActionManager actionManager,String toolbarName,boolean horizontal){
        JComponent actionToolbar = ActionManager.getInstance().createActionToolbar(toolbarName, actionGroup, horizontal).getComponent();
        toolBarPanel.add(actionToolbar, BorderLayout.CENTER);
}
}
