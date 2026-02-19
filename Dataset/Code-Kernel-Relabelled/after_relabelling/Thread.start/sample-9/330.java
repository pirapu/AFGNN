public class func{
public void importRepositories(File file,String onlyLatestVersions,String standardReplacement,String replacements,ProcessBean processBean){
    OptimizedImportController importController = new OptimizedImportController(file, onlyLatestVersions, standardReplacement, replacements, processBean);
    Thread thread = new Thread(importController);
    thread.start();
}
}
