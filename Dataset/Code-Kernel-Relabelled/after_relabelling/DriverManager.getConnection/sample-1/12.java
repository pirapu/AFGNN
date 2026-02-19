public class func{
public void testInListParameterMetaData1(){
        Connection conn = DriverManager.getConnection(getUrl(), PropertiesUtil.deepCopy(TestUtil.TEST_PROPERTIES));
        PreparedStatement statement = conn.prepareStatement(query);
        ParameterMetaData pmd = statement.getParameterMetaData();
        assertEquals(2, pmd.getParameterCount());
        assertEquals(String.class.getName(), pmd.getParameterClassName(1));
}
}
