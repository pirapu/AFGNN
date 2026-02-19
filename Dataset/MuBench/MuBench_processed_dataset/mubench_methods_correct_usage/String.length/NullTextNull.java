void pattern(Object obj) {
  String str = (obj == null ? this.getNullText() : obj.toString());
  if (str != null) {
    str.length();
  }
}
