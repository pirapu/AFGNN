public class func{
public void fillTestTable4(Connection conn,int numberOfRecords){
        for (int id = 0; id < numberOfRecords; id++) {
            String insertQuery = "INSERT INTO " + DatabaseCreator.TEST_TABLE4
                    + " (fk, field1) VALUES(" + id + ", \""
                    + DatabaseCreator.defaultString + id + "\")";
            statement.execute(insertQuery);
        }
}
}
