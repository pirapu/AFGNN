public class func{
public void assertIndexUsed(Connection conn,String query,List<Object> binds,String indexName,boolean expectedToBeUsed){
            for (int i = 0; i < binds.size(); i++) {
                stmt.setObject(i+1, binds.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            String explainPlan = QueryUtil.getExplainPlan(rs);
            assertEquals(expectedToBeUsed, explainPlan.contains(" SCAN OVER " + indexName));
}
}
