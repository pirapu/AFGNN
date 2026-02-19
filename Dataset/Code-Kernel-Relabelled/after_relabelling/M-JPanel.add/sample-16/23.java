public class func{
public void createButtonsPanel(){
            ButtonFactory bf = new ButtonFactory(bundle, this);
            panel.add(bf.createJButton("OKButton"));
            panel.add(bf.createJButton("CancelButton"));
}
}
