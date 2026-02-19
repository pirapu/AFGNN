public class func{
public void paintBackgroundDisabled(Graphics2D g){
    g.setPaint(outerBorderDisabled);
    g.fill(roundRect);
    roundRect = innerBorderRect();
}
}
