public class func{
public void deleteRepositories(RepositoryVO repositoryVO,InfoGluePrincipal principal,Boolean byPassTrashcan,Boolean deleteByForce,ProcessBean processBean){
    DeleteRepositoryController deleteController = new DeleteRepositoryController(repositoryVO, principal, byPassTrashcan, deleteByForce, processBean);
    Thread thread = new Thread(deleteController);
    thread.start();
}
}
