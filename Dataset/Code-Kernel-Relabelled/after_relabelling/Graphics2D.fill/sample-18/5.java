public class func{
public void paintIcon(Component c,Graphics g,int x,int y){
        g2.setPaint(new GradientPaint(0, 0, FROM, ellipse.width, ellipse.height, TO));
        g2.fill(ellipse);
        g2.setPaint(Color.black);
        g2.draw(ellipse);
        g2.setPaint(Color.white);
}
}
