public class func{
public void updateRow(int p1){
    Connection conn = DriverManager.getConnection("jdbc:default:connection");
    PreparedStatement ps = conn.prepareStatement("update t1 set i=i+?");
    ps.setInt(1, p1);
    ps.executeUpdate();
    ps.close();
}
}
