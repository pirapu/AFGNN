public class func{
public void addJCheckbox(String string,JPanel panel,boolean selected){
        JRadioButton radioButton = new JRadioButton(string);
        radioButton.setSelected(selected);
        type.add(radioButton);
        panel.add(radioButton);
}
}
