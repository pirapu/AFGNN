public class func{
public void setUp(){
      connection = DatabaseConfig.getMemoryConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE table_name(" +
                "id INTEGER PRIMARY KEY, name VARCHAR(255), long_value BIGINT," +
                "float_value REAL, double_value DOUBLE, blob_value BINARY, clob_value CLOB );");
}
}
