public class func{
public void initTableValues(byte[][] splits,long ts){
        ensureTableCreated(getUrl(),"LongInKeyTest",splits, ts-2);
        String url = getUrl() + ";" + PhoenixRuntime.CURRENT_SCN_ATTRIB + "=" + ts;
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(true);
        PreparedStatement stmt = conn.prepareStatement(
                "upsert into " +
                "LongInKeyTest VALUES(?)");
        stmt.setLong(1, 2);
        stmt.execute();
}
}
