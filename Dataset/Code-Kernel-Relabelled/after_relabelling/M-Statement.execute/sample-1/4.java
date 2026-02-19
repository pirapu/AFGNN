public class func{
public void testBlob(){
    Statement s = conn.createStatement();
    assertTrue(s.execute("select to_bytes('abc', 'UTF-16')"));
    ResultSet rs = s.getResultSet();
    assertTrue(rs.next());
    byte[] bytes = rs.getBytes(1);
}
}
