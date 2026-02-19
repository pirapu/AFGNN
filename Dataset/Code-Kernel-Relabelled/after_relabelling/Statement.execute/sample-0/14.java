public class func{
public void clearResult(){
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM db_day_sql_fulltext");
        stmt.close();
}
}
