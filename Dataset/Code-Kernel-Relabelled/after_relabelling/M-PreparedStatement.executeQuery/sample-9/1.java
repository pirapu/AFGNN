public class func{
public void selectRows(int p1,ResultSet[] data){
    Connection conn = DriverManager.getConnection("jdbc:default:connection");
    PreparedStatement ps = conn.prepareStatement("select * from t1 where i = ?");
    ps.setInt(1, p1);
    data[0] = ps.executeQuery();
}
}
