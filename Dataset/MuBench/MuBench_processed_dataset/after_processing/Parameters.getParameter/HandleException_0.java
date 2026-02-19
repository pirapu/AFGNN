public class func{
void pattern(Parameters params) {
  String param = null;
  try {
    param = params.getParameter(0);
  } catch (CmdLineException e) {}
  if (param == null) {
  } else {
    param.toLowerCase();
  }
}
}
