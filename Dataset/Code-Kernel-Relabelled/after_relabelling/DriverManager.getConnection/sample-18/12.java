public class func{
public void getReadWriteConnection(){
    if (connection == null) {
      connection = new H2DatabaseConnection(DriverManager.getConnection(databaseUrl));
      if (connectionProxyFactory != null) {
        connection = connectionProxyFactory.createProxy(connection);
      }
    }
}
}
