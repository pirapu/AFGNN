public class func{
public void registerDestinationTables(Connection connection){
        final Statement st = connection.createStatement();
            st.execute("CREATE TABLE dest15(destination INT);" +
                    "INSERT INTO dest15 VALUES (1), (5);" +
                    "CREATE TABLE dest234(destination INT);" +
                    "INSERT INTO dest234 VALUES (2), (3), (4);");
            st.close();
}
}
