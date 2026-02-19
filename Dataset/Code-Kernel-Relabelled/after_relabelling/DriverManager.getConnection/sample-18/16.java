public class func{
public void testCloseDelay(){
        deleteDb("openClose");
        String url = getURL("openClose;DB_CLOSE_DELAY=1", true);
        String user = getUser(), password = getPassword();
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.close();
}
}
