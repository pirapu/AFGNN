public class func{
public void createCBPanel(){
    myCbIsFinal = new JCheckBox(UIUtil.replaceMnemonicAmpersand("Declare &final"));
    panel.add(myCbIsFinal);
    myCbReplaceAllOccurrences = new JCheckBox(UIUtil.replaceMnemonicAmpersand("Replace &all occurrences"));
    panel.add(myCbReplaceAllOccurrences);
}
}
