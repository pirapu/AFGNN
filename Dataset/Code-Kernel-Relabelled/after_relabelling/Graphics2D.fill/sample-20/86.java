public class func{
public void computeRect(PlanarImage[] sourceImages,WritableRaster tile,Rectangle destRect){
        graphics2D.translate(-tile.getMinX(), -tile.getMinY());
        graphics2D.setColor(Color.WHITE);
        graphics2D.fill(shape);
        graphics2D.dispose();
}
}
