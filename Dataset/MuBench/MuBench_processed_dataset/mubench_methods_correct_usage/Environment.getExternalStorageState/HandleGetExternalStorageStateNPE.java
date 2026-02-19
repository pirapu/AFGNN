String pattern() {
  try {
    return Environment.getExternalStorageState();
  } catch (NullPointerException e) {
    return "";
  }
}