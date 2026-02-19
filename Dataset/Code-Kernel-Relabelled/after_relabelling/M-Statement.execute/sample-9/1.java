public class func{
public void testCS4595B_PrimaryKey(){
        Statement st = createStatement();
        st.executeUpdate("set ISOLATION to RR");
        st.execute("call SYSCS_UTIL.SYSCS_SET_RUNTIMESTATISTICS(1)");
        st.executeUpdate("insert into foo values (1, 1)");
        doTestCaseCS4595B(st, "Constraint");
}
}
