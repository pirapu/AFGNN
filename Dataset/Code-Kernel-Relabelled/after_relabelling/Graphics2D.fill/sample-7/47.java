public class func{
public void drawMiddleDecoration(Graphics2D g){
    transform(g, AffineTransform.getTranslateInstance(77, 41));
    g.setColor(GREEN_ALPHA_20);
    g.fill(shape);
    g.setStroke(THIN_STROKE);
    g.setColor(GREEN_ALPHA_48);
    g.draw(shape);
}
}
