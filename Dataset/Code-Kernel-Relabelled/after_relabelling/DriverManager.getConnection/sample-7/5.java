public class func{
public void getConnection(final String url,final String user,final String password){
        if (user != null) {
            LOG.info("Connecting to " + url + " as " + user);
            return DriverManager.getConnection(url, user, password);
        } else {
            LOG.info("Connecting to " + url + " with no credentials");
            return DriverManager.getConnection(url);
        }
}
}
