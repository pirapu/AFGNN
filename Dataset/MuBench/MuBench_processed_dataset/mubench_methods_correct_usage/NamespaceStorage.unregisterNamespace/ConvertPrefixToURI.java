void pattern(NamespaceStorage storage, String prefix) {
  String uri = storage.getURI(prefix);
  storage.unregisterNamespace(uri);
}
