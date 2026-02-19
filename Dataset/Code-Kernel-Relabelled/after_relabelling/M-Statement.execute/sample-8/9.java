public class func{
public void fillTestTable1(Connection conn,int numberOfRecords){
        for (int id = 0; id < numberOfRecords; id++) {
            String value = DatabaseCreator.defaultString + id;
            String insertQuery = "INSERT INTO " + DatabaseCreator.TEST_TABLE1
                    + " (id, field1, field2, field3) VALUES(" + id + ", '"
                    + value + "', " + id + ", " + id + ")";
            statement.execute(insertQuery);
        }
}
}
