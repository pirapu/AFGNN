public class func{
public void submit(ExecutorService execService,Callable<T> proc,int numTasks,String label){
            execService.submit(proc) ;
}
}
