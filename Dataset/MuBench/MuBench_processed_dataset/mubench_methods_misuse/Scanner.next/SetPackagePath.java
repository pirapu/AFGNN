void pattern(Scanner scanner, JavaSource javaSource) {
  while (scanner.hasNext()) {
    String token = scanner.next();
    if (javaSource.getPackagePath() == null && token.equals("package")) {
      
      token = scanner.next();
      if (token.endsWith(";")) {
        String packagePath = token.substring(0, token.indexOf(';'));
        javaSource.setPackagePath(packagePath);
      }
    }
  }
}
