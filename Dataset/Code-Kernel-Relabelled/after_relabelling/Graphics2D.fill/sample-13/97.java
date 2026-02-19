public class func{
public void drawWorld2D(Graphics2D g){
        g.fill(new Rectangle2D.Double(x*LightBotWorldView2D.CELL_WIDTH, y*LightBotWorldView2D.CELL_WIDTH, LightBotWorldView2D.CELL_WIDTH, LightBotWorldView2D.CELL_WIDTH));
    g.setColor(GRID_COLOR);
}
}
