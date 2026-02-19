void mapMayContainNull(Map<String, Object> m) {
	if (m.put("foo", new Object()) != null) {
		// "foo" was set before
	} else {
		// "foo" was not set before (or set to null!)
	}
}
