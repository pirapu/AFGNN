public class func{
public void boxUp(Component a,Component b){
    JPanel newBox = new JPanel();
    newBox.setLayout(new BoxLayout(newBox, BoxLayout.X_AXIS));
    newBox.add(b);
}
}
