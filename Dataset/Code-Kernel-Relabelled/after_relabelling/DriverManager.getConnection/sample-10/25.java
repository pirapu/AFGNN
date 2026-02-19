public class func{
public void testSplit_NullDynamicSeparator(){
        Connection conn = DriverManager.getConnection(getUrl());
        initTable(conn, "ONE,TWO,THREE", null);
        ResultSet rs = conn.createStatement().executeQuery(
                "SELECT REGEXP_SPLIT(VAL, SEP) FROM SPLIT_TEST");
        assertTrue(rs.next());
        assertNull(rs.getString(1));
        assertFalse(rs.next());
}
}
