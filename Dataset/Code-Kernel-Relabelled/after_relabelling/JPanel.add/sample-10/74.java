public class func{
public void createCombinePanel(){
        final JComboBox combineComboBox = new JComboBox();
        bindingCtx.bind("combine", combineComboBox);
        bindingCtx.bindEnabledState("combine", false, "updateMode", true);
        final String displayName = bindingCtx.getPropertySet().getDescriptor("combine").getDisplayName();
        combinePanel.add(new JLabel(displayName + ":"));
        combinePanel.add(combineComboBox);
}
}
