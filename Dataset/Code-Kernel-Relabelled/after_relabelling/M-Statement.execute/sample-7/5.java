public class func{
public void setUp(SQLExecutor sqlExec){
        conn = sqlExec.getConnection();
            stmt = conn.createStatement();
            stmt.execute(createTableSql);
            JdbcUtils.close(stmt);
}
}
