public class func{
public void doRun(Connection cx){
                PreparedStatement ps = open(cx.prepareStatement(log(sql, LOG, section)));
                ps.setString(1, section);
                ResultSet rs = open(ps.executeQuery());
}
}
