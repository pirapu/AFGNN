public class func{
public void testSortOrderForLeadingDescVarLengthColWithNonNullFollowing(){
        Connection conn = DriverManager.getConnection(getUrl());
        conn.createStatement().execute("CREATE TABLE t (k1 VARCHAR, k2 VARCHAR NOT NULL, CONSTRAINT pk PRIMARY KEY (k1 DESC,k2))");
        conn.createStatement().execute("UPSERT INTO t VALUES ('a','x')");
        conn.createStatement().execute("UPSERT INTO t VALUES ('ab', 'x')");
}
}
