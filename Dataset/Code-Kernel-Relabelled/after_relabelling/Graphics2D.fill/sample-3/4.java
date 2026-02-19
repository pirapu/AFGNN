public class func{
public void draw(Graphics2D g,NodeView nodeView,Rectangle r){
    final Shape shape = getShape(r);
    g.setColor(getFillColor(nodeView));
    g.fill(shape);
    g.setColor(edgeColor);
    drawShape(g, shape, r, nodeView);
    g.setColor(color);
}
}
