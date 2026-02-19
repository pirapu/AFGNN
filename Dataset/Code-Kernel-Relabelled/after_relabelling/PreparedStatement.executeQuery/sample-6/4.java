public class func{
public void testUpdateablePreparedStatement(){
    st.close();
    st = con.prepareStatement("select * from updateable where id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    st.setInt(1, 1);
    rs = st.executeQuery();
    rs.moveToInsertRow();
    rs.close();
}
}
