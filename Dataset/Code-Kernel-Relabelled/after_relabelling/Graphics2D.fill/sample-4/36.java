public class func{
public void paintBackgroundDisabled(Graphics2D g){
    path = decodePath5();
    g.setPaint(decodeGradient7(path));
    g.fill(path);
    path = decodePath6();
    g.setPaint(decodeGradient8(path));
    g.fill(path);
}
}
