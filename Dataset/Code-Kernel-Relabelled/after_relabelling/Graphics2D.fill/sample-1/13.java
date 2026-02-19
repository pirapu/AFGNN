public class func{
public void drawFrame(Graphics2D g2d){
        GradientPaint gragient = new GradientPaint(4, 4, controlColor, 13, 13, trimColor);
        g2d.setPaint(gragient);
        g2d.fill(rect);
}
}
