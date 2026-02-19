public static void main(String[] args) {
    Connection connection = null;
    PreparedStatement addAdminPstmt = null;

    try {
        // Establish the connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "user", "password");

        // PreparedStatement is opened here
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        addAdminPstmt = connection.prepareStatement(sql);

        // Set parameters
        addAdminPstmt.setString(1, "admin");
        addAdminPstmt.setString(2, "admin123");
        addAdminPstmt.setString(3, "ADMIN");

        // Execute the update
        addAdminPstmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Close the PreparedStatement and the Connection
        try {
            if (addAdminPstmt != null) addAdminPstmt.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

