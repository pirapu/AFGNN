public class func{
public void createConnection(){
        String password = !config.isPromptForPassword() ? config.getPassword() : promptForPassword();
            Class.forName(config.getDriver());
            return DriverManager.getConnection(config.getUrl(), config.getUsername(), password);
            String pwd = password == null ? "<null>" : StringUtils.repeat("*", password.length());
            System.out.println("Couldn't create JDBC connection to '" + config.getUrl() + "' with username '"
                    + config.getUsername() + "' and password '" + pwd + "', reason: " + ex.getMessage());
}
}
