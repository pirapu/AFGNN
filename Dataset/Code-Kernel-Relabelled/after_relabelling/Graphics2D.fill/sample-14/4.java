public class func{
public void paintProgressBarBorder(final JComponent c,final Graphics2D g2d){
            g2d.setPaint ( new GradientPaint ( 0, shadeWidth, bgTop, 0, c.getHeight () - shadeWidth, bgBottom ) );
            g2d.setPaint ( new GradientPaint ( shadeWidth, 0, bgTop, c.getWidth () - shadeWidth, 0, bgBottom ) );
        g2d.fill ( bs );
        g2d.setPaint ( c.isEnabled () ? this.progressEnabledBorderColor : this.progressDisabledBorderColor );
}
}
