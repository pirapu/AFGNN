public class func{
public void if_two_threads_wait_concurrently_then_both_of_them_will_read_the_same_output(){
        Future<Boolean> t1 = executor.submit(new WaitForOutput("Runtime", process.subscribeToOutput()));
        Future<Boolean> t2 = executor.submit(new WaitForOutput("Runtime", process.subscribeToOutput()));
        process.start();
}
}
