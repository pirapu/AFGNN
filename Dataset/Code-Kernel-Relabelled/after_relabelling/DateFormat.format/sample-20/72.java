public class func{
public void drawDate(final Graphics2D g2,final CheckObject object,final float offset,final Date date){
        TextLayout tdate = new TextLayout(df.format(date), font, frc);
        tdate.draw(g2, dateX, dateY);
}
}
