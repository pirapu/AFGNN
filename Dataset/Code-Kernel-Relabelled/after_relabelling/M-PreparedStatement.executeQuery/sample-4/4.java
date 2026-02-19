public class func{
public void test_0(){
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select ?");
            stmt.setString(1, "aaa");
            ResultSet rs = stmt.executeQuery();
            rs.close();
}
}
