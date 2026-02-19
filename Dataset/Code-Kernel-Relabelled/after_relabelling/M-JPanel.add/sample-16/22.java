public class func{
public void createButtonsPanel(){
        ButtonFactory bf = new ButtonFactory(bundle, this);
        p.add(bf.createJButton("OKButton"));
        p.add(bf.createJButton("CancelButton"));
}
}
