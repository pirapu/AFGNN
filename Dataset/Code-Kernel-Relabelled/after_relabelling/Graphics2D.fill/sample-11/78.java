public class func{
public void composeMaskedImage(ImageCreator isrc,Shape mask,BufferedImage base){
        int wid = base.getWidth();
        int hei = base.getHeight();
        BufferedImage target = isrc.createImage(wid, hei, Transparency.TRANSLUCENT);
        Graphics2D g2 = target.createGraphics();
            g2.setColor(Color.BLACK);
            g2.fill(mask);
            g2.setComposite(AlphaComposite.SrcIn);
            g2.drawImage(base, 0, 0, null);
            g2.dispose();
}
}
