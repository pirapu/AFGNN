public class func{
public void createTable(){
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(ddl);
        stmt.close();
}
}
