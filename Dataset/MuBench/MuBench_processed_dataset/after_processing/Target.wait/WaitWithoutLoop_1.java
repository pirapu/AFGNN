public class func{
void misuse(Target t) throws InterruptedException {
  synchronized (t) {
    if (t.isAvailable()) {
      t.wait();
    }
  }
}
}
