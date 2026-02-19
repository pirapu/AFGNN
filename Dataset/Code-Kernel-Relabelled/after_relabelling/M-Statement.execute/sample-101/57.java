public class func{
public void assertTableExists(String table){
            st.execute(String.format("SELECT count(*) FROM %s;", table));
}
}
