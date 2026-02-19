public class func{
public void loadData(JdbcDataSource ds){
        connection = ds.getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE ROLES AS SELECT * FROM CSVREAD('classpath:roles.csv',null,'lineComment=#')");
        stmt.close();
}
}
