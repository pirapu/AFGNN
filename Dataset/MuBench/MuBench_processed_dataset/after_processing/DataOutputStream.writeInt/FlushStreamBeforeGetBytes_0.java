public class func{
void pattern(OutputStream out) throws IOException {
  DataOutputStream dout = new DataOutputStream(out);
  dout.writeInt(0);
  dout.flush();
}
}
