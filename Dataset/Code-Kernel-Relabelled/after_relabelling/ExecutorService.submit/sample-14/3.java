public class func{
public void run(){
                for (int i = 0; i < 100; i++) {
                    pool.submit(new TestRunnable(stripe, actual, i));
                }
                pool.shutdown();
}
}
