public class func{
public void dropTable(Connection con,String table){
        Statement stmt = con.createStatement();
        stmt.execute("DROP TABLE " + table + " IF EXISTS;");
}
}
