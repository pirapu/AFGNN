public class func{
public void getBucketByKey(Object key){
            ps.setString(1, String.valueOf(keyHash));
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getObject(dataColumnName);
                }
                factory.releaseConnection(connection);
}
}
