String pattern(Scanner scanner) {
  if (!scanner.hasNext()) {
    throw new IllegalArgumentException("Insufficient number of tokens");
  }
  return scanner.next();
}
