public class func{
public void checkInstanceGroup(Connection conn,String groupName,long accountId){
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM  instance_group WHERE name = ? and account_id = ?");
        pstmt.setString(1, groupName);
        pstmt.setLong(2, accountId);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return false;
        } else {
            return true;
        }
}
}
