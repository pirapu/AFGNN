public class func{
public void getResizableIconFromResource(String resource){
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            image = ImageIO.read(cl.getResource(resource));
            LOGGER.error("Failed to read image: \"{}\"", resource);
}
}
