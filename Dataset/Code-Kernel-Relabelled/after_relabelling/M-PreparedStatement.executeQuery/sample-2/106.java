public class func{
public void testModifySmallBlobs(){
        PreparedStatement ps = prepareStatement(
                "select dBlob, length from smallBlobs");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Blob Blob = rs.getBlob(1);
            int length = rs.getInt(2);
            Blob.setBytes(length, "X".getBytes("US-ASCII"));
            byte[] content = Blob.getBytes(1, 100);
        }
        rs.close();
}
}
