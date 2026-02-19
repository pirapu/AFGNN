public class func{
public void getTestBundleURL(String artifactName,String baseName,String version){
        assertNotNull("Version cannot be null!", version);
        File f = new File(m_testBundleBasePath, String.format("%1$s/target/org.apache.felix.deploymentadmin.test.%2$s-%3$s.jar", artifactName, baseName, version));
        assertTrue("No such bundle: " + f, f.exists() && f.isFile());
        return f.toURI().toURL();
}
}
