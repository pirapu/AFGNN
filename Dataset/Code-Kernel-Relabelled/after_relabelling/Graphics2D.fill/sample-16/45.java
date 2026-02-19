public class func{
public void paintComponent(final Graphics g){
        final Ellipse2D circle2 = new Ellipse2D.Double(two.getX() - 6,
                two.getY() - 5, 10, 10);
        g2.draw(circle1);
        g2.fill(circle1);
        g2.draw(circle2);
        g2.fill(circle2);
        final Line2D line = new Line2D.Double(one, two);
}
}
