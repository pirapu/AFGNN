public class func{
public void execQueryBug5191(PreparedStatement pStmt,int catId){
    pStmt.setInt(1, catId);
    this.rs = pStmt.executeQuery();
    assertTrue(this.rs.next());
    assertTrue(this.rs.next());
    assertFalse(this.rs.next());
}
}
