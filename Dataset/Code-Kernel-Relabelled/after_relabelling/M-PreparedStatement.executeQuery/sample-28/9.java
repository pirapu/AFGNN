public class func{
public void executeIntQuery(Connection connection,String queryString,int[] args){
  for( int i = 0; i < args.length; ++i )
    {
    pStatement.setInt( i + 1, args[ i ] );
    }
return pStatement.executeQuery();
}
}
