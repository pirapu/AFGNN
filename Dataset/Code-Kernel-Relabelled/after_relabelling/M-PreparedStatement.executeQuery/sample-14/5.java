public class func{
public void positiveTest(String sql,Object expected){
            ps = prepareStatement(sql);
            rs = ps.executeQuery();
            JDBC.assertFullResultSet(rs, new Object[][] {{expected}}, false, /*closeResultSet=*/true);
            if (ps != null) { ps.close(); }
}
}
