void pattern(Target t) throws InterruptedException {
  synchronized (t) {
    
    t.wait();
    // Perform action appropriate to condition
  }
}
