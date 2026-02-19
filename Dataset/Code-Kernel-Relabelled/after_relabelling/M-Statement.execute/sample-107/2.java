public class func{
public void setUp(){
        con = TestUtil.openDB();
        Statement stmt = con.createStatement();
        stmt.execute("CREATE DOMAIN testdom AS int4 CHECK (value < 10)");
}
}
