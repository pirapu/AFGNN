public class func{
public void importRepositories(String[] repositoryIds,InfoGluePrincipal principal,String onlyLatestVersions,String standardReplacement,String replacements,ProcessBean processBean){
    CopyRepositoryController copyController = new CopyRepositoryController(repositoryIds, principal, onlyLatestVersions, standardReplacement, replacements, processBean);
    Thread thread = new Thread(copyController);
    thread.start();
}
}
