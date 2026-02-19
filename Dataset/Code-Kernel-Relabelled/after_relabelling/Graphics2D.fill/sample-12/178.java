public class func{
public void paintHudControlBackground(Graphics2D graphics,Rectangle bounds,ShapeProvider shapeProvider,Paint paint){
        graphics.fill(shapeProvider.createShape(x, y + 1, width, height - 1));
        graphics.setColor(BORDER_COLOR);
}
}
