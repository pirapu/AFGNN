public class func{
public void createTable(){
        Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE T (FID INT, FNAME VARCHAR2(4000), FDESC CLOB)");
        stmt.close();
}
}
