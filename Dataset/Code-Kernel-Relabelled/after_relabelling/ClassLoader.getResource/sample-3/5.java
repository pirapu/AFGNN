public class func{
public void testTemplateResources(){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("mail.html");
        File file = new File(url.getFile());
        TestCase.assertNotNull(file);
}
}
