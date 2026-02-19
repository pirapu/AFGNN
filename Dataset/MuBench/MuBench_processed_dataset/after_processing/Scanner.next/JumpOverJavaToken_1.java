public class func{
protected static String jumpOverJavaToken(String token, Scanner scanner) {
  String nextToken = token;
  while (isAJavaToken(nextToken)) {
    if (nextToken.startsWith("@") && nextToken.indexOf('(') > -1 && !nextToken.endsWith(")")) {
      nextToken = scanAfterClosedParenthesis(nextToken, scanner);
    } else {
      nextToken = scanner.next();
    }
  }
  return nextToken;
}
}
