public class func{
public void paintObject(Graphics2D g2,State s,ObjectInstance ob,float cWidth,float cHeight){
      int building = ob.getIntValForAttribute(FrostbiteDomain.BUILDINGATTNAME);
      for (; layer < Math.min(maxLayer, building) - 1; layer++) {
        if (layer == maxLayer / 3) {
          brickHeight /= 2;
          iglooOffsety = -(layer - 1) * brickHeight;
        }
        if (layer >= maxLayer / 3) {
          iglooWidth -= FrostbiteDomain.gameWidth / (4 * maxLayer);
          iglooOffsetx += FrostbiteDomain.gameWidth / (8 * maxLayer);
        }
        g2.fill(new Rectangle2D.Double(iglooOffsetx + 3 * FrostbiteDomain.gameWidth / 4,
            iglooOffsety + FrostbiteDomain.gameHeight / 5 - brickHeight * layer,
            iglooWidth, brickHeight));
      }
      if (building >= maxLayer) {
        g2.setColor(Color.black);
        int doorWidth = FrostbiteDomain.gameWidth / 28;
        int doorHeight = FrostbiteDomain.gameHeight / 20;
        g2.fill(new Rectangle2D.Double(3 * FrostbiteDomain.gameWidth / 4 + FrostbiteDomain.gameWidth / 12 - doorWidth / 2,
            FrostbiteDomain.gameHeight / 5 - doorHeight/2, doorWidth, doorHeight));
      }
}
}
