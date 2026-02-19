public void misuse(Object o) {
	o.hashCode();
	if (o == null) {
		o = new Object();
	}
}
