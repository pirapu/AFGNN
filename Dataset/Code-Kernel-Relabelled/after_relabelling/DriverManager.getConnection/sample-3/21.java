public class func{
public void testSelectWithSubtractionExpression(){
        Connection conn = DriverManager.getConnection(getUrl(), props);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            assertTrue (rs.next());
            assertEquals(rs.getString(1), ROW8);
            assertEquals(rs.getInt(2), 0);
            assertFalse(rs.next());
            conn.close();
}
}
