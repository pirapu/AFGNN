public class func{
int pattern(SQLiteDatabase mDb, String TABLE, String[] STATE, String WHERE, String[] COND) {
  Cursor c = mDb.query(TABLE, STATE, WHERE, COND, null, null, null);
  if (c.getCount() < 1) {
    c.close();
    return 0;
  }
  c.moveToFirst();
  int reportState = c.getInt(0);
  c.close();
  return reportState;
}
}
