public class func{
public void createPageUIContentPane(JPanel pane){
        JPanel contentPane = GridBagUtils.createPanel();
        final GridBagConstraints gbc = GridBagUtils.createConstraints("fill=HORIZONTAL,anchor=NORTHWEST");
        contentPane.add(pane, gbc);
        GridBagUtils.addVerticalFiller(contentPane, gbc);
}
}
