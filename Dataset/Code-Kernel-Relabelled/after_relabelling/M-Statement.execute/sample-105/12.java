public class func{
public void onRelease(JDBCDataStore store,Connection cx){
                st = cx.createStatement();
                st.execute(command);
                store.closeSafe(st);
}
}
