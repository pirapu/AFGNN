public class func{
public void createRedLightImage(final int WIDTH,final int HEIGHT){
        final int IMAGE_WIDTH = IMAGE.getWidth();
        final int IMAGE_HEIGHT = IMAGE.getHeight();
        final Ellipse2D FRAME = new Ellipse2D.Double(0.10204081632653061 * IMAGE_WIDTH, 0.046762589928057555 * IMAGE_HEIGHT, 0.7959183673469388 * IMAGE_WIDTH, 0.2805755395683453 * IMAGE_HEIGHT);
        G2.setPaint(new LinearGradientPaint(new Point2D.Double(0.5 * IMAGE_WIDTH, 0.046762589928057555 * IMAGE_HEIGHT), new Point2D.Double(0.5 * IMAGE_WIDTH, 0.3273381294964029 * IMAGE_HEIGHT), new float[]{0.0f, 0.05f, 0.1f, 0.17f, 0.27f, 1.0f}, new Color[]{new Color(1f, 1f, 1f, 1f), new Color(0.8f, 0.8f, 0.8f, 1f), new Color(0.6f, 0.6f, 0.6f, 1f), new Color(0.4f, 0.4f, 0.4f, 1f), new Color(0.2f, 0.2f, 0.2f, 1f), new Color(0.0039215686f, 0.0039215686f, 0.0039215686f, 1f)}));
        G2.fill(FRAME);
        final Ellipse2D INNER_CLIP = new Ellipse2D.Double(0.10204081632653061 * IMAGE_WIDTH, 0.0683453237410072 * IMAGE_HEIGHT, 0.7959183673469388 * IMAGE_WIDTH, 0.2589928057553957 * IMAGE_HEIGHT);
        G2.setPaint(new LinearGradientPaint(new Point2D.Double(0.5 * IMAGE_WIDTH, 0.0683453237410072 * IMAGE_HEIGHT), new Point2D.Double(0.5 * IMAGE_WIDTH, 0.3273381294964029 * IMAGE_HEIGHT), new float[]{0.0f, 0.35f, 0.66f, 1.0f}, new Color[]{new Color(0f, 0f, 0f, 1f), new Color(0.0156862745f, 0.0156862745f, 0.0156862745f, 1f), new Color(0f, 0f, 0f, 1f), new Color(0.0039215686f, 0.0039215686f, 0.0039215686f, 1f)}));
        G2.fill(INNER_CLIP);
        final Ellipse2D LIGHT_EFFECT = new Ellipse2D.Double(0.14285714285714285 * IMAGE_WIDTH, 0.06474820143884892 * IMAGE_HEIGHT, 0.7142857142857143 * IMAGE_WIDTH, 0.2517985611510791 * IMAGE_HEIGHT);
        G2.setPaint(new RadialGradientPaint(new Point2D.Double(0.5 * IMAGE_WIDTH, 0.1906474820143885 * IMAGE_HEIGHT), (0.3622448979591837f * IMAGE_WIDTH), new float[]{0.0f, 0.88f, 0.95f, 1.0f}, new Color[]{new Color(0f, 0f, 0f, 1f), new Color(0f, 0f, 0f, 1f), new Color(0.3686274510f, 0.3686274510f, 0.3686274510f, 1f), new Color(0.0039215686f, 0.0039215686f, 0.0039215686f, 1f)}));
        G2.fill(LIGHT_EFFECT);
        final Ellipse2D INNER_SHADOW = new Ellipse2D.Double(0.14285714285714285 * IMAGE_WIDTH, 0.06474820143884892 * IMAGE_HEIGHT, 0.7142857142857143 * IMAGE_WIDTH, 0.2517985611510791 * IMAGE_HEIGHT);
        G2.setPaint(new LinearGradientPaint(new Point2D.Double(0.5 * IMAGE_WIDTH, 0.0683453237410072 * IMAGE_HEIGHT), new Point2D.Double(0.5 * IMAGE_WIDTH, 0.29856115107913667 * IMAGE_HEIGHT), new float[]{0.0f, 1.0f}, new Color[]{new Color(0f, 0f, 0f, 1f), new Color(0.0039215686f, 0.0039215686f, 0.0039215686f, 0f)}));
        G2.fill(INNER_SHADOW);
        G2.dispose();
}
}
