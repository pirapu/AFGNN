void misuse(Target t) throws InterruptedException {
  synchronized (t) {
    if (t.isAvailable()) {
      t.wait();
    }
    // Perform action appropriate to condition
  }
}

