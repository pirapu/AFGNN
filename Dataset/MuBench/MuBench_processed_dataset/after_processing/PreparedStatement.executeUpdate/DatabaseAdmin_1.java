public class func{
public static void main(String[] args) {
    Connection connection = null;
    PreparedStatement addAdminPstmt = null;
    try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "user", "password");
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        addAdminPstmt = connection.prepareStatement(sql);
        addAdminPstmt.setString(1, "admin");
        addAdminPstmt.setString(2, "admin123");
        addAdminPstmt.setString(3, "ADMIN");
        addAdminPstmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
