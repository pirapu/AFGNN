public void pattern(OutputStream out, String value) throws IOException {
	Writer writer = null;
	try {
		writer = new PrintWriter(out);
		writer.write(value);
	} finally {
		
	}
}
