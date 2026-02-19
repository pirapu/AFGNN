public class func{
public void testDecimalRounding(int id,String input,String output){
        String stringdata = String.format("My Nuncle Vanya says: case %d: (%s -> %s)",
                                          id, input, output);
        ps.setBigDecimal(1, new BigDecimal(input));
        ps.setString(2, stringdata);
        ps.executeUpdate();
        ps = conn.prepareStatement("select ID from T_DECIMAL where value = ?;");
        ps.setString(1, stringdata);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            BigDecimal value = rs.getBigDecimal(1);
            assertEquals(new BigDecimal(output), value);
        }
}
}
