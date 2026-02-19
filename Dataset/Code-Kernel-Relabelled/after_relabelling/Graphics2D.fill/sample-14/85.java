public class func{
public void paintIcon(final Component c,final Graphics2D g2d,final int x,final int y,final int w,final int h){
                g2d.setPaint ( new RadialGradientPaint ( center, radius, fractions, cb.isEnabled () ? colors : disabledColors ) );
                g2d.fill ( shape );
}
}
