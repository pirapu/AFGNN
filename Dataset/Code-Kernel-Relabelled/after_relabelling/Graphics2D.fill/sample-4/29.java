public class func{
public void paintBackgroundDisabled(Graphics2D g){
    rect = decodeRect1();
    g.setPaint(decodeGradient3(rect));
    g.fill(rect);
    rect = decodeRect2();
    g.setPaint(decodeGradient4(rect));
    g.fill(rect);
}
}
