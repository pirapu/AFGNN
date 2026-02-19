public class func{
public void lockPatchTable(String database){
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:" + database, "sa", "");
        PreparedStatement stmt = conn.prepareStatement("UPDATE patches SET patch_in_progress = 'T' WHERE patch_level in ( SELECT MAX(patch_level) FROM patches WHERE system_name = ?)");
        stmt.setString(1, database);
        int rowCount = stmt.executeUpdate();
        assertEquals(1, rowCount);
        SqlUtil.close(conn, stmt, null);
}
}
