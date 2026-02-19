public class func{
public void testCastWithUnaryMinusAndPlus(){
      PreparedStatement ps = prepareStatement("select cast(-? as smallint), cast(+? as int) from t1");
      ps.setInt(2,2);
      JDBC.assertParameterTypes(ps,expectedTypes);
      JDBC.assertFullResultSet(ps.executeQuery(), expectedRows, true);
      ps.close();
}
}
