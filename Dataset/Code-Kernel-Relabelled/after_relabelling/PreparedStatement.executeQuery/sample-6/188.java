public class func{
public void testDateFuncWithParam(){
        if (!TestUtil.haveMinimumServerVersion(con, "8.0"))
            return;
        PreparedStatement ps = con.prepareStatement("SELECT {fn timestampadd(SQL_TSI_QUARTER, ? ,{fn now()})}, {fn timestampadd(SQL_TSI_MONTH, ?, {fn now()})} ");
        ps.setInt(2, 12);
        ResultSet rs = ps.executeQuery();
        assertTrue(rs.next());
        assertEquals(rs.getTimestamp(1), rs.getTimestamp(2));
}
}
