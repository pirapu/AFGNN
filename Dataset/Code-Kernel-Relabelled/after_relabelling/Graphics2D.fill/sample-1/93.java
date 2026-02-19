public class func{
public void paintBackgroundDefaultAndPressed(Graphics2D g){
    g.setPaint(focusBorder);
    g.fill(roundRect);
    roundRect = innerBorderRect();
}
}
