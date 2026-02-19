public class func{
public void testViewParameters(){
        deleteDb("cases");
        Connection conn = getConnection("cases");
        Statement stat = conn.createStatement();
        stat.execute(
                "create view test as select 0 value, 'x' name from dual");
}
}
