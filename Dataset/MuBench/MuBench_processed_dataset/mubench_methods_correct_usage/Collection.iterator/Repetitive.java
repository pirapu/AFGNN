public String misuse(Collection<Object> objects) {
	String value = null;
	for (Object object : objects) {
		value = objects.iterator().next().toString();
	}
	return value;
}
