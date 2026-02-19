public class func{
public void runStatement(VersionedPostgisDataStore dataStore,String sqlStatement){
            conn = dataStore.getConnection(Transaction.AUTO_COMMIT);
            st = conn.createStatement();
            st.execute(sqlStatement);
            JDBCUtils.close(conn, Transaction.AUTO_COMMIT, null);
}
}
