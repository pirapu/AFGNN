public class func{
public void removeTable(Connection conn,String name){
    Statement stat = conn.createStatement();
    stat.execute("DROP TABLE "+name);
}
}
