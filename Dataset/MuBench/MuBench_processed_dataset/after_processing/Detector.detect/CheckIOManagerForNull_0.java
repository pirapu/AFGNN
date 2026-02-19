public class func{
void detect(IOManager ioManager, Metadata metadata) throws IOException {
  Detector detector = ioManager.getDetector();
  if (detector != null) {
    detector.detect(null, metadata);
  }
}
}
