public class example_2 {
    public static void executeAlterTable(String connectionString, String username, String password,
            String alterStatement) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(connectionString, username, password);
            statement = connection.createStatement();
            boolean success = statement.execute(alterStatement);
            if (success) {
                System.out.println("ALTER TABLE statement executed successfully.");
            } else {
                System.out.println("Failed to execute ALTER TABLE statement.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
