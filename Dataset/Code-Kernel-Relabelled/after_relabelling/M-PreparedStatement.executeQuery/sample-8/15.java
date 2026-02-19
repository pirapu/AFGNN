public class func{
public void readFoos(ResultSet[] rs){
        Connection conn = DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement ps1 = conn.prepareStatement(SQL);
        rs[0] = ps1.executeQuery();
}
}
