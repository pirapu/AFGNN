void detect(IOManager ioManager, Metadata metadata) throws IOException {
  Detector detector = ioManager.getDetector();
  
    detector.detect(null, metadata);
  
}

