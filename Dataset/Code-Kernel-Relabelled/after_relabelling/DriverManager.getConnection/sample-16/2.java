public class func{
public void testBindTypeMismatch(){
        PhoenixConnection pconn = DriverManager.getConnection(getUrl(), TEST_PROPERTIES).unwrap(PhoenixConnection.class);
        ColumnResolver resolver = FromCompiler.getResolver(statement, pconn);
        StatementContext context = new StatementContext(new PhoenixStatement(pconn), resolver, binds, scan);
            WhereCompiler.compile(context, statement);
}
}
