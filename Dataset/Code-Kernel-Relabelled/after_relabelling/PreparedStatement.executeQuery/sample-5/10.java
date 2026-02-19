public class func{
public void selectRows(int p1,int p2,int p3,int p4,ResultSet[] rs1,ResultSet[] rs2){
    PreparedStatement ps2 = conn.prepareStatement("select * from account where acc_id in (?,?)");
    ps2.setInt(2, p4);
    rs2[0] = ps2.executeQuery();
}
}
