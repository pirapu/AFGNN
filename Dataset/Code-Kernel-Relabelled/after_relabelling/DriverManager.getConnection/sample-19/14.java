public class func{
public void openConnection(){
        final JvstmOJBConfig config = getConfig();
        final String url = "jdbc:mysql:" + config.getDbAlias();
        final Connection connection = DriverManager.getConnection(url, config.getDbUsername(), config.getDbPassword());
        connection.setAutoCommit(false);
}
}
