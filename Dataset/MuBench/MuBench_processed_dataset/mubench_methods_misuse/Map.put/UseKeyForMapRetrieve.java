void get(Map innerCache, Object key) throws IOException {
  Object value;
  if (innerCache == null) {
    innerCache = new HashMap();
    value = null;
  } else {
    value = innerCache.get(key);
  }
  
    value = new CreationPlaceholder();
    innerCache.put(reader, value);
  
}
