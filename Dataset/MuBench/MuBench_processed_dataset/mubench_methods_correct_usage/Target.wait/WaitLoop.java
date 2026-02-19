void pattern(Target t) throws InterruptedException {
  synchronized (t) {
    while (t.isAvailable()) {
      t.wait();
    }
    // Perform action appropriate to condition
  }
}
