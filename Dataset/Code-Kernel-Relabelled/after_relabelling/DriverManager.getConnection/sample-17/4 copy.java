public class func{
public void update(int id,String value2,int flag){
        Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, flag);
        stmt.setString(2, value2);
        stmt.setInt(3, id);
        stmt.executeUpdate();
        stmt.close();
}
}
