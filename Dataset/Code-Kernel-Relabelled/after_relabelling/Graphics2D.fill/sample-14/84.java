public class func{
public void paintIconBackground(final Graphics2D g2d,final int x,final int y){
        g2d.setPaint ( new RadialGradientPaint ( cx, cy, radius, fractions, getBgColors () ) );
        g2d.fill ( shape );
        final Stroke os = GraphicsUtils.setupStroke ( g2d, borderStroke );
}
}
