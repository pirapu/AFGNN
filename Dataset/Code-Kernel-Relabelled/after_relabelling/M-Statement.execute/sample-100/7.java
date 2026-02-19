public class func{
public void testGeneratedKeys(){
    Statement s = conn.createStatement();
    assertFalse(s.execute("insert into x (z) values (1)", Statement.RETURN_GENERATED_KEYS));
    ResultSet rs = s.getGeneratedKeys();
    rs.next();
    assertEquals(1, rs.getInt(1));
}
}
