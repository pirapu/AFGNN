public class func{
public void testCaseCS4595B_NonUniqueIndex(){
        Statement st = createStatement();
        st.executeUpdate("set ISOLATION to RR");
        st.execute("call SYSCS_UTIL.SYSCS_SET_RUNTIMESTATISTICS(1)");
        st.executeUpdate("insert into foo values (1, 1)");
        doTestCaseCS4595B(st, "Index");
}
}
