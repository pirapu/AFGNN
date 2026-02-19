public class func{
public void addAlignCenter(JComponent target,JPanel owner){
    p.add(Box.createHorizontalGlue());
    p.add(target);
    p.add(Box.createHorizontalGlue());
    owner.add(p);
}
}
