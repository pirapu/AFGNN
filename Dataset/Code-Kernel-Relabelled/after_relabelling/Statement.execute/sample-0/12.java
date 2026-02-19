public class func{
public void setSchema(String schemaName){
        Connection c = DriverManager.getConnection("jdbc:default:connection");
        Statement s = c.createStatement();
        s.execute("SET SCHEMA " + schemaName);
        s.close();
}
}
