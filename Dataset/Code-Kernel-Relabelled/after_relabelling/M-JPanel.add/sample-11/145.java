public class func{
public void wrapItemForSelection(JComponent comp){
        panel.setBackground(UIManager.getColor("MenuItem.selectionBackground"));
        panel.add(comp);
        panel.setOpaque(false);
}
}
