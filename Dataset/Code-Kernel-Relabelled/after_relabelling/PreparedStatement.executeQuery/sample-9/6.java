public class func{
public void foo(Connection connection){
    ps.setString(getIntValue(), "");
    ps.setInt(1, 0);
    ResultSet rs = ps.executeQuery();
    rs.getString(0);
}
}
