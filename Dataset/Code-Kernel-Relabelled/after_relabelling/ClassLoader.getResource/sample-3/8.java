public class func{
public void testNonMetaInfNotAccessible(){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL nonManifestResource = cl.getResource("example2.txt");
        assertNull(nonManifestResource);
}
}
