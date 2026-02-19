public class func{
void pattern(Target t) throws InterruptedException {
  synchronized (t) {
    t.wait();
  }
}
}
