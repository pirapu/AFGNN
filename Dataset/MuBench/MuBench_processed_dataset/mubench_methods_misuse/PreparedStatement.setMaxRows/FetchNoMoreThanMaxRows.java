void pattern(PreparedStatement stmt) {
  stmt.setMaxRows(maxRows);
  int fetchSize = 10000;
  
    fetchSize = maxRows; // JCR-3090
  
  stmt.setFetchSize(fetchSize);
}