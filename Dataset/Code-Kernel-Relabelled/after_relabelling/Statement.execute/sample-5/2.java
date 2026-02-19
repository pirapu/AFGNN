public class func{
public void testSetMode(){
        deleteDb(getTestName());
        c1 = getConnection(getTestName());
        Statement stat = c1.createStatement();
        stat.execute("SET LOCK_MODE 2");
        ResultSet rs = stat.executeQuery("call lock_mode()");
        rs.next();
        assertEquals("2", rs.getString(1));
        c1.close();
}
}
