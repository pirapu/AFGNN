public class func{
public void addRevertButton(final JPanel panel){
        final JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING, 4, 0));
        p.setBackground(Browser.BUTTON_PANEL_BACKGROUND);
        p.add(revertButton);
        panel.add(p, BorderLayout.CENTER);
}
}
