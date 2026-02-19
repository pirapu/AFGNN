public class func{
public void testSetNonString(){
    Statement s = conn.createStatement();
    assertFalse(s.execute("SET extra_float_digits TO 2"));
    assertTrue(s.execute("SHOW extra_float_digits"));
    ResultSet rs = s.getResultSet();
    assertTrue(rs.next());
    assertEquals("2", rs.getString(1));
}
}
