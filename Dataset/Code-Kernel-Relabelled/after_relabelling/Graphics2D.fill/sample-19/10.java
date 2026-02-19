public class func{
public void paintComponent(final Graphics g){
                final Shape border = createBorderShape ();
                GraphicsUtils.drawShade ( g2d, border, WebCustomTooltipStyle.shadeColor, shadeWidth );
                g2d.setPaint ( bg );
                g2d.fill ( border );
                g2d.setPaint ( Color.WHITE );
                g2d.draw ( border );
                GraphicsUtils.restoreAntialias ( g2d, aa );
}
}
