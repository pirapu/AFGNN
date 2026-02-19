public class func{
public void getCharacterIdAndNameFromDatabase(String name){
    Connection con = DatabaseConnection.getConnection();
    PreparedStatement ps = con.prepareStatement("SELECT id, name, buddyCapacity FROM characters WHERE name LIKE ?");
    ps.setString(1, name);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      ret = new CharacterIdNameBuddyCapacity(rs.getInt("id"), rs.getString("name"), rs.getInt("buddyCapacity"));
    }
    rs.close();
}
}
