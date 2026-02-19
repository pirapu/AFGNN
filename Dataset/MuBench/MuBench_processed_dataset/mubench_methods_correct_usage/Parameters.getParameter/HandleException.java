void pattern(Parameters params) {
  String param = null;
  try {
    param = params.getParameter(0);
  } catch (CmdLineException e) {}
  
  if (param == null) {
    // do something
  } else {
    param.toLowerCase();
  }
}
