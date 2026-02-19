public class func{
public void deleteAllUsers(){
    Connection conn = DriverManager.getConnection( "jdbc:default:connection" );
    PreparedStatement ps = conn.prepareStatement( "delete from t_user" );
    int count = ps.executeUpdate();
    ps.close();
}
}
