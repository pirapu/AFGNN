
public void pattern(File file) throws IOException {
	if (file.exists()) {
		try (FileInputStream fis = new FileInputStream(file)) {
	// do something with fis...
	}
	}
}

