public class func{
public void testSplit_ArrayReference(){
        Connection conn = DriverManager.getConnection(getUrl());
        initTable(conn, "ONE,TWO,THREE");
        ResultSet rs = conn.createStatement().executeQuery(
                "SELECT REGEXP_SPLIT(VAL, ',')[1] FROM SPLIT_TEST");
        assertTrue(rs.next());
        assertEquals("ONE", rs.getString(1));
        assertFalse(rs.next());
}
}
