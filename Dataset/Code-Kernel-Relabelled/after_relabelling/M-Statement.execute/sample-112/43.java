public class func{
public void test_recycle(){
        DruidPooledConnection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("select 1");
}
}
