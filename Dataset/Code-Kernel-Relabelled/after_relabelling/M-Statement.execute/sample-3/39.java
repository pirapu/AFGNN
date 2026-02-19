public class func{
public void createNodeTable(Connection connection,TableLocation nodesName){
        final Statement st = connection.createStatement();
            st.execute("CREATE TABLE " + nodesName + "(" +
                    NODE_ID + " INTEGER PRIMARY KEY, " +
                    CONNECTED_COMPONENT + " INTEGER);");
            st.close();
}
}
