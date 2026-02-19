public class func{
public void test_find_file_in_jar(){
        URL url = getClass().getResource("/org/nutz/lang/one.jar");
        assertNotNull(url);
        ClassLoader classLoader = URLClassLoader.newInstance(new URL[]{url});
        InputStream is = Files.findFileAsStream("org/nutz/plugin/Plugin.w",
                                                classLoader.loadClass("org.nutz.lang.XXXX"));
        assertNotNull(is);
        assertEquals(is.available(), 133);
}
}
