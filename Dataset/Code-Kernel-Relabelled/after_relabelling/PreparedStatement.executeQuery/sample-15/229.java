public class func{
public void doRun(Connection cx){
                String sql = format("SELECT val FROM %s WHERE nid = ? AND key = ?", PROPS);
                PreparedStatement ps = open(cx.prepareStatement(log(sql, LOG, node, key)));
                ps.setString(2, key);
                ResultSet rs = open(ps.executeQuery());
                if (rs.next()) {
                    return rs.getString(1);
                } else {
                    return null;
                }
}
}
