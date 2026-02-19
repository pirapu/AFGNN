public class func{
void pattern(PreparedStatement stmt) {
  stmt.setMaxRows(maxRows);
  int fetchSize = 10000;
    fetchSize = maxRows;
  stmt.setFetchSize(fetchSize);
}
}
