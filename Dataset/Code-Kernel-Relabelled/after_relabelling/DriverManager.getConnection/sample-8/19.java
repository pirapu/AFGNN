public class func{
public void get(){
            if (username != null || password != null)
                return DriverManager.getConnection(url, username, password);
            else
                return DriverManager.getConnection(url);
}
}
