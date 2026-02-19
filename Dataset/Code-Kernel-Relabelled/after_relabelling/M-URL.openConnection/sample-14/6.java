public class func{
public void test_getManifest(){
        URL u = copyAndOpenResourceStream("lf.jar", "plus.bmp");
        juc = (JarURLConnection) u.openConnection();
        Manifest mf = juc.getManifest();
        assertNotNull(mf);
        assertEquals(mf, juc.getManifest());
        assertNotSame(mf, juc.getManifest());
        assertEquals(juc.getMainAttributes(), mf.getMainAttributes());
}
}
