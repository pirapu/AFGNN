public class func{
public void testCreateArrayWithNonStandardDelimiter(){
        PreparedStatement pstmt = _conn.prepareStatement("SELECT ?::box[]");
        pstmt.setArray(1, _conn.createArrayOf("box", in));
        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        Array arr = rs.getArray(1);
        ResultSet arrRs = arr.getResultSet();
        assertTrue(arrRs.next());
        assertEquals(in[0], arrRs.getObject(2));
        assertTrue(arrRs.next());
        assertEquals(in[1], arrRs.getObject(2));
        assertFalse(arrRs.next());
}
}
