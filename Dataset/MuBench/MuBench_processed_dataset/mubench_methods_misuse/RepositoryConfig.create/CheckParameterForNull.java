void pattern() {
  String configFile = parameters.get(JCAManagedConnectionFactory.CONFIGFILE_KEY);
  String homeDir = parameters.get(JCAManagedConnectionFactory.HOMEDIR_KEY);
 
    RepositoryConfig.create(configFile, homeDir);
  
}

