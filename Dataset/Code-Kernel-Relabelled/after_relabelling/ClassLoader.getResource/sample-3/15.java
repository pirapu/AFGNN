public class func{
public void testMetaInfAccessible(){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL manifestResource = cl.getResource("META-INF/example.txt");
        assertNotNull(manifestResource);
}
}
