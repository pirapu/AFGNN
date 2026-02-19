public class example_10 {
    public void testPreparedStatement() {
        Connection connection = proxyDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from emp where id = ?");
        if (statement.exists()) {
            statement.setInt(1, 2);
            statement.executeQuery();
        }
    }
}
