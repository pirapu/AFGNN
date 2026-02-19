public class func{
public void testCacheHintTtl(){
    Statement s = conn.createStatement();
    s.execute("set showplan on");
    ResultSet rs = s.executeQuery("/*+ cache(ttl:50) */ select 1");
    assertTrue(rs.next());
    s.execute("set noexec on");
}
}
