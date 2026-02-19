public class func{
public void drawGenericNominalLegend(Graphics graphics,String[] names,PointStyle[] pointStyles,Color[] colors,int xOffset,int alpha){
            g.setColor(color);
            g.fill(shape);
            g.setColor(Color.black);
            g.draw(shape);
            g.drawString(nominalValue, currentX, 15);
            Rectangle2D stringBounds = LABEL_FONT.getStringBounds(nominalValue, g.getFontRenderContext());
            currentX += stringBounds.getWidth() + 15;
}
}
