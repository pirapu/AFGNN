public class func{
public void onBorrow(JDBCDataStore store,Connection cx){
                st = cx.createStatement();
                st.execute("SET @MYVAR = " + value);
                store.closeSafe(st);
}
}
