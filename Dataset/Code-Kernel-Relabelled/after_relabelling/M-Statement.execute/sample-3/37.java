public class func{
public void insertFoo(Connection conn,int id,String name){
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO foo (id, name) VALUES (" + id + ", '" + name + "')");
        stmt.close();
}
}
