public class func{
public void flowPanelCenter(int hgap,int vgap,int tab,Color bgColor,Component... comps){
    for (Component comp : comps) {
      p.add(comp);
    }
    p.setBackground(bgColor);
}
}
