public class func{
public void cleanUpDerbyDatabases(){
        conn = DriverManager.getConnection(dbURL);
        Statement stat = conn.createStatement();
        stat.executeUpdate("DROP TABLE books");
        stat.close();
}
}
