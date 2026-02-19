public class func{
public void parameter1(int a,String b,String c,java.sql.ResultSet[] rs){
        Connection conn = DriverManager
                .getConnection("jdbc:default:connection");
        PreparedStatement ps = conn
                .prepareStatement("insert into PT1 values (?, ?, ?)");
        ps.setInt(1, a);
        ps.setString(3, c);
        ps.executeUpdate();
        ps.close();
        ps = conn.prepareStatement(
            "select a,b, length(b), c, length(c) from PT1 where a = ?");
        ps.setInt(1, a);
        rs[0] = ps.executeQuery();
}
}
