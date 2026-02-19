public class func{
public void createActionsToolbar(ActionGroup ag){
    ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, ag, false);
    actionToolbar.setOrientation(SwingConstants.VERTICAL);
    rv.add(actionToolbar.getComponent());
}
}
