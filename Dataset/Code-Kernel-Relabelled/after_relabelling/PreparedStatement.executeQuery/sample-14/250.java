public class func{
public void executeStmt(PreparedStatement stmt){
            ResultSet rs = stmt.executeQuery();
            d.watch(rs);
            while (rs.next()) {
                m_rowProcessor.processRow(rs);
                m_count++;
            }
}
}
