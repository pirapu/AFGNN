public class func{
public void createTimeWindow(){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        DEACTIVATED_VALIDATION_XML = new File(cl.getResource("META-INF/deactivated_validation.xml").toURI());
        ACTIVATED_VALIDATION_XML = new File(DEACTIVATED_VALIDATION_XML.getCanonicalPath().replace("deactivated_", ""));
        assertFalse(ACTIVATED_VALIDATION_XML.exists());
        boolean renamingSucceeded = DEACTIVATED_VALIDATION_XML.renameTo(ACTIVATED_VALIDATION_XML);
        assertTrue(renamingSucceeded);
}
}
