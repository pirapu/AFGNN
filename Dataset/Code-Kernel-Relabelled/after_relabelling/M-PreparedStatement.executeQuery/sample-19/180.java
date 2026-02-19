public class func{
public void testStatementDescribe(){
      ResultSet rs = pstmt.executeQuery();
      assertTrue(rs.next());
      assertEquals(2, rs.getInt(1));
      rs.close();
}
}
