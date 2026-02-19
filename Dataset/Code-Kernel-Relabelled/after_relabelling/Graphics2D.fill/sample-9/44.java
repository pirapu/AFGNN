public class func{
public void draw(Graphics2D g2,Rectangle2D area,Object params){
        if (this.backgroundPaint != null) {
            g2.setPaint(this.backgroundPaint);
            g2.fill(area);
        }
        area = trimPadding(area);
        return this.container.draw(g2, area, params);
}
}
