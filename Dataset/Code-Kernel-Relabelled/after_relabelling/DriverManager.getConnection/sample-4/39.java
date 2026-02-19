public class func{
public void testNewerTableDisallowed(){
        props.setProperty(PhoenixRuntime.CURRENT_SCN_ATTRIB, Long.toString(ts + 5));
        Connection conn5 = DriverManager.getConnection(PHOENIX_JDBC_URL, props);
        conn5.createStatement().executeUpdate("ALTER TABLE " + ATABLE_NAME + " DROP COLUMN x_integer");
            conn5.createStatement().executeUpdate("ALTER TABLE " + ATABLE_NAME + " DROP COLUMN y_integer");
}
}
