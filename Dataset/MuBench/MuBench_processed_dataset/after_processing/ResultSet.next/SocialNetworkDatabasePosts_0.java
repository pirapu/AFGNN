public class func{
public static void main(String[] args) {
    Connection connection = null;
    Statement statement = null;
    ResultSet postResult = null;
    try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "user", "password");
        statement = connection.createStatement();
        postResult = statement.executeQuery("SELECT * FROM posts");
        while (postResult.next()) {
            System.out.println("Post ID: " + postResult.getInt("id"));
            System.out.println("Post Title: " + postResult.getString("title"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (postResult != null) postResult.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
