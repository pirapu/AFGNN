public class func{
public void getPopupComponent(User user,Project project){
    panel.add(new BoldLabel(StringUtil.getMsg("0.from.1", getTitle(), user.getDisplayName())), BorderLayout.NORTH);
    ComponentConsoleView componentConsoleView = new ComponentConsoleView(user, project);
    outputMessage(componentConsoleView);
    panel.add(componentConsoleView.getComponent());
}
}
