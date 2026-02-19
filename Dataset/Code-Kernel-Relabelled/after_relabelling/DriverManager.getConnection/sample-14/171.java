public class func{
public void main(String[] args){
      Connection conn = DriverManager.getConnection("jdbc:mdbtools:" + filename);
      ResultSet rset = conn.getMetaData().getTables(null,null,null,null);
      while (rset.next())
        System.out.println(rset.getString("table_name"));
      rset.close();
}
}
