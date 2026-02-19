public class func{
public void findAllCoffeeBeverages(ResultSet[] coffeeBeverages){
    connection = DriverManager.getConnection("jdbc:default:connection");
    statement = connection.prepareStatement(sql);
    coffeeBeverages[0] = statement.executeQuery();
}
}
