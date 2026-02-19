public class func{
public void testOrTrueFilter(){
        PhoenixConnection pconn = DriverManager.getConnection(getUrl(), PropertiesUtil.deepCopy(TEST_PROPERTIES)).unwrap(PhoenixConnection.class);
        PhoenixPreparedStatement pstmt = newPreparedStatement(pconn, query);
        QueryPlan plan = pstmt.optimizeQuery();
        Scan scan = plan.getContext().getScan();
        Filter filter = scan.getFilter();
        assertNull(filter);
        byte[] startRow = PVarchar.INSTANCE.toBytes(tenantId);
}
}
