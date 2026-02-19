public class func{
public void paint(Graphics2D g,double factor,int xOffset,int yOffset,boolean dotSelection,String control1,String control2,String control3){
      g.drawOval((int) (refPoint2.x * factor) - 10 + xOffset, (int) (refPoint2.y * factor) - 10 + yOffset, 20, 20);
      g.fill(new Rectangle2D.Double(refPoint2.x * factor - 1 + xOffset, refPoint2.y * factor - 1 + yOffset, 3, 3));
}
}
