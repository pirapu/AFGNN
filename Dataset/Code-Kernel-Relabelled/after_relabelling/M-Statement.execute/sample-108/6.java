public class func{
public void testIsClosed(){
        assertFalse(conn.isClosed());
        conn.close();
        assertTrue(conn.isClosed());
        conn = DriverManager.getConnection("jdbc:sqlite:/" + dbFile.getPath());
        assertFalse(conn.isClosed());
        Statement st = conn.createStatement();
        st.execute("select * from zoo");
}
}
