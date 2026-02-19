public class func{
public void testBadUrl(){
    URL url = new File("nonexistent.web.xml").toURI().toURL();
    builder.expectWarn("Unable to process '" + url.toExternalForm()
        + "' for servlet validation", IOException.class);
    UnitTestTreeLogger logger = builder.createLogger();
    ServletValidator validator = ServletValidator.create(logger, url);
    assertNull(validator);
    logger.assertCorrectLogEntries();
}
}
