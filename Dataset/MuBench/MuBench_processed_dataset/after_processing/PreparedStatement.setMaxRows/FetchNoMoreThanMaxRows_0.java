public class func{
void pattern(PreparedStatement stmt) {
  stmt.setMaxRows(maxRows);
  int fetchSize = 10000;
  if (0 < maxRows && maxRows < fetchSize) {
    fetchSize = maxRows;
  }
  stmt.setFetchSize(fetchSize);
}
}
