public class func{
public void testBooleanIntegerBind(){
        pstmt.setObject(1, new Boolean(true), java.sql.Types.INTEGER);
        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
        rs.close();
}
}
