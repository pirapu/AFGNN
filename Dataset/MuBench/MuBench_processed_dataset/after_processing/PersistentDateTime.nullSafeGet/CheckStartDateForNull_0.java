public class func{
void nullSafeGet(ResultSet resultSet, String[] names) throws HibernateException, SQLException {
  PersistentDateTime pst = new PersistentDateTime();
  DateTime start = (DateTime) pst.nullSafeGet(resultSet, names[0]);
  DateTime end = (DateTime) pst.nullSafeGet(resultSet, names[1]);
  if (start != null && end != null) {
      new Interval(start, end);
  }
}
}
