public class func{
public void createContentPanelWithAdvancedSettingsPanel(){
    final JPanel advancedSettings = createAdvancedSettings();
    if (advancedSettings != null) {
      scrollPanel.add(advancedSettings, BorderLayout.CENTER);
    }
}
}
