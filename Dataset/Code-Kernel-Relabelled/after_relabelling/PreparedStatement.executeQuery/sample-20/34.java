public class func{
public void getDatabaseProperty(String propertyName,Connection conn){
        PreparedStatement getDBP =  conn.prepareStatement(
        "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY(?)");
        getDBP.setString(1, propertyName);
        ResultSet rs = getDBP.executeQuery();
        rs.next();
        String value = rs.getString(1);
        rs.close();
}
}
