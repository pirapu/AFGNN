public class func{
void pattern(HashMap codeMap, Class exceptionClass) {
  Integer code = (Integer) codeMap.get(exceptionClass);
  if (code != null) {
    code.intValue();
  }
}
}
